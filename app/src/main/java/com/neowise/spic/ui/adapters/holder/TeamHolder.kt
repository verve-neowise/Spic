package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.model.Team
import com.neowise.spic.ui.adapters.MarkableCallback
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter

class TeamsAdapter(callback: TeamCallback) : ListAdapter<Team, TeamCallback>(TeamHolderFactory(), callback)

class MarkableTeamsAdapter(callback: MarkableCallback<Team>) : ListAdapter<Team, MarkableCallback<Team>>(MarkableTeamHolderFactory(), callback)

class TeamHolderFactory : HolderFactory<Team, TeamCallback> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Team, TeamCallback> {
        val view = inflater.inflate(R.layout.item_team, group, false)
        return TeamHolder(view)
    }
}

class MarkableTeamHolderFactory : HolderFactory<Team, MarkableCallback<Team>> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Team, MarkableCallback<Team>> {
        val view = inflater.inflate(R.layout.item_team, group, false)
        return MarkableTeamHolder(view)
    }
}

class MarkableTeamHolder(view: View) : Holder<Team, MarkableCallback<Team>>(view) {
    override fun bind(value: Team, callback: MarkableCallback<Team>) {
        itemView.findViewById<TextView>(R.id.teamCode).text = value.code
        itemView.findViewById<TextView>(R.id.teamName).text = value.name
        itemView.findViewById<TextView>(R.id.teamActivity).text = value.activity
        val checkbox = itemView.findViewById<ImageView>(R.id.checkbox)

        if (value.marked) {
            checkbox.visibility = View.VISIBLE
        }
        else {
            checkbox.visibility = View.GONE
        }

        itemView.setOnClickListener { callback.markableStateChanged(value, adapterPosition) }
    }
}

class TeamHolder(view: View) : Holder<Team, TeamCallback>(view) {

    override fun bind(value: Team, callback: TeamCallback) {
        itemView.findViewById<TextView>(R.id.teamCode).text = value.code
        itemView.findViewById<TextView>(R.id.teamName).text = value.name
        itemView.findViewById<TextView>(R.id.teamActivity).text = value.activity

        itemView.setOnClickListener { callback.onTeamSelected(value) }
    }
}

interface TeamCallback : Callback {
    fun onTeamSelected(team: Team)
}