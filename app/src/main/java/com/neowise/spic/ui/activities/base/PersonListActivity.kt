package com.neowise.spic.ui.activities.base

import android.view.View
import com.neowise.spic.model.Person
import com.neowise.spic.ui.adapters.holder.PersonCallback
import com.neowise.spic.ui.adapters.holder.PersonListAdapter
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

abstract class PersonListActivity(titleRes: Int)
    : ListActivity<Person, PersonCallback>(titleRes), PersonCallback {

    override fun initialize() {
        adapter(PersonListAdapter(false, this))
        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        refresh()
    }

    protected fun refresh() {
        binding.swipeRefresh.isRefreshing = true
        binding.itemList.visibility = View.INVISIBLE
        loadItems()
    }

    fun loadItems() {

        RunIO<List<Person>>()
            .run(this::fetchData)
            .onSuccess{
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

    abstract fun fetchData(): List<Person>
}