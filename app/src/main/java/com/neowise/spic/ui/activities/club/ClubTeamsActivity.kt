package com.neowise.spic.ui.activities.club

import android.view.View
import android.widget.Toast
import com.neowise.spic.R
import com.neowise.spic.model.Team
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.ListActivity
import com.neowise.spic.ui.adapters.holder.TeamCallback
import com.neowise.spic.ui.adapters.holder.TeamsAdapter
import com.neowise.spic.ui.dialogs.TeamEditDialog
import com.neowise.spic.ui.dialogs.TeamEditDialog.TeamEditListener
import com.neowise.spic.ui.dialogs.TeamOverviewDialog
import com.neowise.spic.ui.dialogs.TeamOverviewDialog.TeamOverviewListener
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class ClubTeamsActivity : ListActivity<Team, TeamCallback>(R.string.teams), TeamCallback, TeamEditListener, TeamOverviewListener {

    override fun initialize() {
        adapter(TeamsAdapter(this))

        enableAddButton(R.string.add_team)
        enableSearching(true)

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }

        refresh()
    }

    private fun refresh() {
        binding.swipeRefresh.isRefreshing = true
        binding.itemList.visibility = View.INVISIBLE
        loadTeams()
    }

    private fun loadTeams() {
        RunIO<List<Team>>()
            .run {
                Services.clubService.getTeams()
            }
            .onSuccess(::setItems)
            .onFailure { errorToast(this, it) }
            .execute()
    }

    override fun onAddClicked() {
        TeamEditDialog().show(supportFragmentManager, "create team")
    }

    override fun onSearch(text: String) {
        if (text.isNotEmpty()) {
            filter { it.name.contains(text, true) }
        }
        else {
            recoverFilter()
        }
    }

    override fun onTeamSelected(team: Team) {
        TeamOverviewDialog(team).show(supportFragmentManager, "team")
    }

    override fun onTeamEdited(team: Team, method: TeamEditDialog.EditMethod) {
        RunIO<Unit>()
            .run {
                if (method == TeamEditDialog.EditMethod.CREATE) {
                    Services.teamService.createTeam(team)
                }
                else {
                    Services.teamService.updateTeam(team.id, team)
                }
            }
            .onSuccess {
                Toast.makeText(this, "Successfuly", Toast.LENGTH_SHORT).show()
                refresh()
            }
            .onFailure { errorToast(this, it) }
            .execute()
    }

    override fun teamRemoved(team: Team) {
        refresh()
    }
}