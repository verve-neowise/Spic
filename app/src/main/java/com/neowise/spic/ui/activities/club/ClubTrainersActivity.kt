package com.neowise.spic.ui.activities.club

import android.content.Intent
import com.neowise.spic.Const
import com.neowise.spic.R
import com.neowise.spic.model.Person
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.PersonListActivity
import com.neowise.spic.ui.adapters.holder.Action
import com.neowise.spic.ui.adapters.holder.ActionCallback
import com.neowise.spic.ui.dialogs.ActionDialog
import com.neowise.spic.ui.dialogs.ProfileOverviewDialog

class ClubTrainersActivity : PersonListActivity(R.string.all_trainers), ActionCallback {

    private var selectedTrainer: Person? = null

    override fun initialize() {
        super.initialize()
        enableSearching(true)
    }

    override fun fetchData(): List<Person> {
        return Services.clubService.getTrainers()
    }

    override fun onPersonSelected(person: Person, position: Int) {
        selectedTrainer = person

        ActionDialog(
            actions = listOf(Action.TEAMS, Action.PROFILE),
            callback = this
        ).show(supportFragmentManager, "action")
    }

    override fun onAction(action: Action) {
        selectedTrainer?.let { trainer ->
            when(action) {
                Action.TEAMS -> {
                    val intent = Intent(this, MarkedTeamsActivity::class.java)
                    intent.putExtra(Const.TRAINER_ID, trainer.id)
                    startActivity(intent)
                }
                Action.PROFILE -> {
                    ProfileOverviewDialog(trainer).show(supportFragmentManager, "profile")
                }
                else -> throw NoSuchElementException()
            }
        }
    }
}