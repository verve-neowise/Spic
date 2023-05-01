package com.neowise.spic.ui.activities.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.model.Role
import com.neowise.spic.databinding.ActivityWhoAreBinding
import com.neowise.spic.model.Token
import com.neowise.spic.Preferences
import com.neowise.spic.ui.activities.ActivityRoute
import com.neowise.spic.ui.adapters.holder.RoleAdapter
import com.neowise.spic.ui.adapters.holder.RoleCallback

class RoleSelectionActivity : AppCompatActivity(), RoleCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWhoAreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roleList = listOf(Role.PLAYER, Role.TRAINER, Role.LEADER, Role.PARENT, Role.NONE)

        val roleAdapter = RoleAdapter(this)
        roleAdapter.setItems(roleList)

        binding.roleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roleAdapter
        }
    }

    override fun onRoleSelected(role: Role) {
        Preferences.instance(this).token = Token("token", role)
        val activity = ActivityRoute[role] ?: return
        startActivity(Intent(this, activity))
    }
}