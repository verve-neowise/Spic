package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.neowise.spic.R
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter

enum class Action(val text: Int, val danger: Boolean = false) {
    BIND_TEAM(R.string.bind_team),
    REMOVE_TEAM(R.string.remove_team),
    PROFILE(R.string.profile),
    CHANGE_TEAM(R.string.change_team),
    PLAYER(R.string.as_player),
    TRAINER(R.string.as_trainer),
    PARENT(R.string.as_parent),
    LEADER(R.string.as_managment),
    NONE(R.string.as_none),
    SHOW_CHILDREN(R.string.children),
    ADD_CHILD(R.string.add_child),
    DETAILS(R.string.details),
    EDIT(R.string.edit),
    REMOVE(R.string.delete, true),
    REMOVE_USER(R.string.delete_user, true),
    TEAMS(R.string.teams),
}

class ActionAdapter(callback: ActionCallback) : ListAdapter<Action, ActionCallback>(ActionHolderFactory(), callback)

class ActionHolderFactory : HolderFactory<Action, ActionCallback> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Action, ActionCallback> {
        val view = inflater.inflate(R.layout.item_action, group, false)
        return ActionHolder(view)
    }
}

class ActionHolder(view: View) : Holder<Action, ActionCallback>(view) {

    override fun bind(value: Action, callback: ActionCallback) {
        val actionName = itemView.findViewById<TextView>(R.id.actionName)
        actionName.setText(value.text)

        if (value.danger) {
            actionName.setTextColor(ContextCompat.getColor(itemView.context, R.color.accent_red))
        }

        itemView.setOnClickListener { callback.onAction(value) }
    }
}

interface ActionCallback : Callback {
    fun onAction(action: Action)
}