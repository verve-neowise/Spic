package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.model.Role
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter

fun RoleAdapter(callback: RoleCallback): ListAdapter<Role, RoleCallback> {
    return ListAdapter(RoleHolderFactory(), callback)
}

class RoleHolderFactory : HolderFactory<Role, RoleCallback> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Role, RoleCallback> {
        val view = inflater.inflate(R.layout.item_role, group, false)
        return RoleHolder(view)
    }
}

class RoleHolder(view: View) : Holder<Role, RoleCallback>(view) {
    override fun bind(value: Role, callback: RoleCallback) {
        val textView = itemView.findViewById<TextView>(R.id.text)
        textView.setText(value.text)

        itemView.setOnClickListener { callback.onRoleSelected(value) }
    }
}

interface RoleCallback : Callback {
    fun onRoleSelected(role: Role)
}