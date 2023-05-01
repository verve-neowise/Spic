package com.neowise.spic.ui.activities.club

import android.view.View
import com.neowise.spic.Const
import com.neowise.spic.R
import com.neowise.spic.Services
import com.neowise.spic.model.Team
import com.neowise.spic.ui.activities.base.ListActivity
import com.neowise.spic.ui.adapters.MarkableCallback
import com.neowise.spic.ui.adapters.holder.MarkableTeamsAdapter
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class MarkedTeamsActivity : ListActivity<Team, MarkableCallback<Team>>(R.string.trainer_teams), MarkableCallback<Team> {

    private var trainerId = -1

    override fun initialize() {
        trainerId = intent!!.getIntExtra(Const.TRAINER_ID, -1)
        adapter(MarkableTeamsAdapter(this))
        enableSearching(true)
        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        refresh()
    }

    protected fun refresh() {
        binding.swipeRefresh.isRefreshing = true
        binding.itemList.visibility = View.INVISIBLE
        loadTeams()
    }

    private fun loadTeams() {
        RunIO<List<Team>>()
            .run { Services.teamService.getTeamsByTrainer(trainerId) }
            .onSuccess {
                setItems(it)
            }
            .onFailure { errorToast(this, it) }
            .execute()
    }

    override fun onSearch(text: String) {
        if (text.isNotEmpty()) {
            filter { it.name.contains(text, true) }
        }
        else {
            recoverFilter()
        }
    }

    override fun markableStateChanged(markable: Team, position: Int) {
        RunIO<Unit>()
            .run {
                if (markable.marked) {
                    Services.clubService.removeTeamFromTrainer(trainerId, markable.id)
                }
                else {
                    Services.clubService.addTeamToTrainer(trainerId, markable.id)
                }
            }
            .onSuccess {
                markable.marked = !markable.marked
                adapter.notifyItemChanged(position)
            }
            .onFailure {
                errorToast(this, it)
            }
            .execute()
    }
}