package com.neowise.spic.ui.activities.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.databinding.AbstractActivityItemListBinding
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.ListAdapter

abstract class ListActivity<V, CB: Callback>(private val titleRes: Int) : AppCompatActivity(), SearchView.OnQueryTextListener {

    internal lateinit var binding: AbstractActivityItemListBinding
    protected lateinit var adapter: ListAdapter<V, CB>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AbstractActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.setText(titleRes)
        binding.itemList.layoutManager = LinearLayoutManager(this)
        binding.searchView.setOnQueryTextListener(this)

        initialize()
    }

    protected fun enableAddButton(textRes: Int) {
        binding.addButton.visibility = View.VISIBLE
        binding.addButton.setText(textRes)
        binding.addButton.setOnClickListener { onAddClicked() }
    }

    protected fun enableSearching(isEnabled: Boolean) {
        binding.searchView.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

    protected fun setItems(items: List<V>) {
        binding.swipeRefresh.isRefreshing = false
        binding.itemList.visibility = View.VISIBLE
        this.adapter.setItems(items)
    }

    protected fun adapter(adapter: ListAdapter<V, CB>) {
        this.adapter = adapter
        this.binding.itemList.adapter = adapter
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        onSearch(query.trim())
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        onSearch(newText.trim())
        return false
    }

    protected open fun onSearch(text: String) { }

    protected open fun onAddClicked() { }

    protected fun recoverFilter() {
        adapter.recoverFilter()
    }

    protected fun filter(filter: (V) -> Boolean) {
        adapter.filter(filter)
    }

    abstract fun initialize()
}