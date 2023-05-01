package com.neowise.spic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.databinding.DialogTeamListBinding
import com.neowise.spic.model.Team
import com.neowise.spic.ui.adapters.holder.TeamCallback
import com.neowise.spic.ui.adapters.holder.TeamsAdapter
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class TeamSelectDialog(val teamProvider: suspend () -> List<Team>, val callback: (Team) -> Unit)
    : DialogFragment(), SearchView.OnQueryTextListener, TeamCallback {

    private lateinit var binding: DialogTeamListBinding
    private lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTeamListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamsAdapter = TeamsAdapter(this)
        binding.searchView.setOnQueryTextListener(this)
        binding.teamList.adapter = teamsAdapter
        binding.teamList.layoutManager = LinearLayoutManager(context!!)

        loadItems()
    }

    private fun loadItems() {
        RunIO<List<Team>>()
            .run { teamProvider() }
            .onSuccess(teamsAdapter::setItems)
            .onFailure { errorToast(context!!, it) }
            .execute()
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            val query = it.trim()
            if (query.isNotEmpty()) {
                teamsAdapter.filter { it.name.contains(query, true) }
            }
            else {
                teamsAdapter.recoverFilter()
            }
        }
        return false
    }

    override fun onTeamSelected(team: Team) {
        callback(team)
        dismiss()
    }
}