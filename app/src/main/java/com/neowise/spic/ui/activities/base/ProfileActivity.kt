package com.neowise.spic.ui.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.databinding.ActivityProfileBinding
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.ui.activities.profiles.menu.MenuNode
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.MenuCallback
import com.neowise.spic.ui.adapters.holder.MenuHolderFactory

abstract class ProfileActivity : AppCompatActivity(), MenuCallback {

    internal lateinit var binding: ActivityProfileBinding

    private val menuNodes = mutableListOf<MenuNode>()
    private val menuList = mutableListOf<Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.teamName.text = ""
        binding.name.text = ""
        binding.birthDate.text = ""

        initialize()

        val menuListAdapter = ListAdapter(MenuHolderFactory(false), this@ProfileActivity)

        binding.menuList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuListAdapter
        }

        menuListAdapter.setItems(menuList)
    }

    final override fun onMenuSelected(menu: Menu) {
        menuNodes.find { it.menu == menu }?.let {
            it.action()
        }
    }

    protected fun addMenu(menu: Menu, action: () -> Unit) {
        menuNodes += MenuNode(menu, action)
        menuList += menu
    }

    protected abstract fun initialize()
}