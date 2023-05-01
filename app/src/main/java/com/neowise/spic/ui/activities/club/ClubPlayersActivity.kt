package com.neowise.spic.ui.activities.club

import android.widget.Toast
import com.neowise.spic.R
import com.neowise.spic.model.Person
import com.neowise.spic.model.Team
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.PersonListActivity
import com.neowise.spic.ui.adapters.holder.Action
import com.neowise.spic.ui.adapters.holder.ActionCallback
import com.neowise.spic.ui.dialogs.ActionDialog
import com.neowise.spic.ui.dialogs.PlayerOverviewDialog
import com.neowise.spic.ui.dialogs.TeamSelectDialog
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class ClubPlayersActivity : PersonListActivity(R.string.all_players), ActionCallback {

    private var selectedPerson: Person? = null

    override fun initialize() {
        super.initialize()
        enableSearching(true)
    }

    override fun fetchData(): List<Person> {
        return Services.clubService.getPlayers()
    }

    override fun onPersonSelected(person: Person, position: Int) {

        selectedPerson = person

        ActionDialog(
            actions = listOf(Action.CHANGE_TEAM, Action.PROFILE),
            callback = this
        ).show(supportFragmentManager, "action")
    }

    override fun onAction(action: Action) {
        selectedPerson?.let { player ->
            when(action) {
                Action.CHANGE_TEAM -> {
                    TeamSelectDialog(::allTeams) { team ->
                        RunIO<Unit>()
                            .run {
                                Services.clubService.changePlayerTeam(player.id, team.id)
                            }
                            .onSuccess {
                                Toast.makeText(
                                    this,
                                    "${player.name} team changed to ${team.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                refresh()
                            }
                            .onFailure { errorToast(this, it) }
                            .execute()
                    }.show(supportFragmentManager, "")
                }
                Action.PROFILE -> {
                    PlayerOverviewDialog(player).show(supportFragmentManager, "profile")
                }
                else -> throw NoSuchElementException()
            }
        }
    }

    private suspend fun allTeams(): List<Team> {
        return Services.clubService.getTeams()
    }

    inline fun runIO(crossinline block: () -> Unit) {
        RunIO<Unit>()
            .run { block() }
            .onFailure { errorToast(this, it) }
            .execute()
    }
}