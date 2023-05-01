package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter


fun MenuListAdapter(isHorizontal: Boolean, callback: MenuCallback): ListAdapter<Menu, MenuCallback> {
    return ListAdapter(MenuHolderFactory(isHorizontal), callback)
}

class MenuHolderFactory(private val isHorizontal: Boolean) : HolderFactory<Menu, MenuCallback> {

    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Menu, MenuCallback> {
        val view = inflater.inflate(getLayout(), group, false)
        return MenuHolder(view)
    }

    private fun getLayout() = if (isHorizontal) {
        R.layout.item_dialog_menu
    } else {
        R.layout.item_menu
    }
}

class MenuHolder(view: View) : Holder<Menu, MenuCallback>(view) {

    override fun bind(value: Menu, callback: MenuCallback) {

        val icon = itemView.findViewById<ImageView>(R.id.item_icon)
        val title = itemView.findViewById<TextView>(R.id.item_title)

        icon.setImageResource(value.icon)
        title.setText(value.title)

        itemView.setOnClickListener { callback.onMenuSelected(value) }
    }
}

interface MenuCallback : Callback {
    fun onMenuSelected(menu: Menu)
}