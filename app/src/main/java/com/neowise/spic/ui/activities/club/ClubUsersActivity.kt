package com.neowise.spic.ui.activities.club

import android.view.View
import com.neowise.spic.R
import com.neowise.spic.model.Role
import com.neowise.spic.Services
import com.neowise.spic.model.User
import com.neowise.spic.ui.activities.base.ListActivity
import com.neowise.spic.ui.adapters.holder.*
import com.neowise.spic.ui.dialogs.ActionDialog
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class ClubUsersActivity : ListActivity<User, UserCallback>(R.string.all_users), UserCallback {

    override fun initialize() {
        adapter(UsersAdapter(this))
        enableSearching(true)

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        refresh()
    }

    private fun refresh() {
        binding.swipeRefresh.isRefreshing = true
        binding.itemList.visibility = View.INVISIBLE
        fetchData()
    }

    fun fetchData() {
        RunIO<List<User>>()
            .run {
                val users = Services.clubService.getAllUsers()
                users.sortedBy {
                    when(it.role) {
                        Role.NONE -> -1
                        Role.LEADER -> 1
                        else -> 0
                    }
                }
            }
            .onSuccess(::setItems)
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

    override fun userSelected(user: User) {
        ActionDialog(listOf(Action.PLAYER, Action.TRAINER, Action.PARENT, Action.LEADER, Action.NONE, Action.REMOVE_USER), object : ActionCallback {

            override fun onAction(action: Action) {

                if (action == Action.REMOVE_USER) {
                    RunIO<Unit>()
                        .run {
                            Services.clubService.deleteUser(user.id)
                        }
                        .onSuccess {
                            adapter.removeElement(user)
                        }
                        .onFailure { errorToast(this@ClubUsersActivity, it) }
                        .execute()
                    return
                }

                val role = when(action) {
                    Action.PARENT -> Role.PARENT
                    Action.PLAYER -> Role.PLAYER
                    Action.TRAINER -> Role.TRAINER
                    Action.LEADER -> Role.LEADER
                    Action.NONE -> Role.NONE
                    else -> throw IllegalArgumentException(action.name)
                }

                RunIO<Unit>()
                    .run {
                        Services.clubService.changeUserRole(user.id, role)
                    }
                    .onSuccess {
                        adapter.changeItem(user, user.copy(role = role, teamName = user.teamName ?: ""))
                    }
                    .onFailure { errorToast(this@ClubUsersActivity, it) }
                    .execute()
            }
        }).apply {
            show(supportFragmentManager, "tag")
        }
    }
}