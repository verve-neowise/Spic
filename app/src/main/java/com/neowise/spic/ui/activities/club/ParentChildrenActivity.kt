package com.neowise.spic.ui.activities.club

import android.view.View
import com.neowise.spic.Const
import com.neowise.spic.R
import com.neowise.spic.Services
import com.neowise.spic.model.Person
import com.neowise.spic.ui.activities.base.ListActivity
import com.neowise.spic.ui.adapters.holder.PersonCallback
import com.neowise.spic.ui.adapters.holder.PersonListAdapter
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class ParentChildrenActivity : ListActivity<Person, PersonCallback>(R.string.children), PersonCallback {

    private var parentId = -1

    override fun initialize() {
        parentId = intent!!.getIntExtra(Const.PARENT_ID, -1)
        adapter(PersonListAdapter(true, this))
        enableSearching(true)
        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        refresh()
    }

    private fun refresh() {
        binding.swipeRefresh.isRefreshing = true
        binding.itemList.visibility = View.INVISIBLE
        loadChildren()
    }

    private fun loadChildren() {
        RunIO<List<Person>>()
            .run { Services.profileService.parentChildren(parentId) }
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

    override fun onPersonSelected(person: Person, position: Int) {
        RunIO<Unit>()
            .run {
                if (person.marked) {
                    Services.clubService.removeTeamFromTrainer(parentId, person.id)
                }
                else {
                    Services.clubService.addChildToParent(parentId, person.id)
                }
            }
            .onSuccess {
                adapter.changeItem(person, person.copy(marked = !person.marked, teamName = ""))
            }
            .onFailure {
                errorToast(this, it)
            }
            .execute()
    }
}