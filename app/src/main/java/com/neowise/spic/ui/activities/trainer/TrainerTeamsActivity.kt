package com.neowise.spic.ui.activities.trainer

import android.view.View
import com.neowise.spic.R
import com.neowise.spic.Const
import com.neowise.spic.model.Team
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.ListActivity
import com.neowise.spic.ui.adapters.holder.TeamCallback
import com.neowise.spic.ui.adapters.holder.TeamsAdapter
import com.neowise.spic.ui.dialogs.TeamOverviewDialog
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class TrainerTeamsActivity : ListActivity<Team, TeamCallback>(R.string.teams), TeamCallback {

    private var trainerId: Int = 0

    override fun initialize() {

        trainerId = intent.getIntExtra(Const.TRAINER_ID, 0)

        adapter(TeamsAdapter(this))
        enableSearching(false)
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
                Services.teamService.getTeamsByTrainer(trainerId)
            }
            .onSuccess (::setItems)
            .onFailure { errorToast(this, it) }
            .execute()
    }

    override fun onTeamSelected(team: Team) {
            TeamOverviewDialog(team).show(supportFragmentManager, "team")
    }
}