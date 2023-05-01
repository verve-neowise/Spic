package com.neowise.spic.ui.adapters.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class ListAdapter<V, CB : Callback>(
    private val holderFactory: HolderFactory<V, CB>,
    private val callback: CB
) : RecyclerView.Adapter<Holder<V, CB>>() {

    private var orginalItems: MutableList<V> = mutableListOf()
    private var items: MutableList<V> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<V, CB> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return holderFactory.createHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: Holder<V, CB>, position: Int) {
        holder.bind(items[position], callback)
    }

    fun setItems(items: List<V>) {
        this.orginalItems = items.toMutableList()
        this.items = orginalItems
        notifyDataSetChanged()
    }

    fun changeItem(item: V, newItem: V) {
        val index = items.indexOf(item)
        val index2 = orginalItems.indexOf(item)
        if (index != -1) {
            items[index] = newItem
        }
        if (index2 != -1) {
            orginalItems[index2] = newItem
        }
        notifyItemChanged(index)
    }

    fun getItems() = items

    fun recoverFilter() {
        this.items = orginalItems
        notifyDataSetChanged()
    }

    fun filter(filter: (V) -> Boolean) {
        this.items = orginalItems.filter(filter) as MutableList<V>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    fun removeElement(item: V) {
        val index = items.indexOf(item)
        val originalIndex = orginalItems.indexOf(item)
        if (index != -1) {
            items.removeAt(index)
        }
        if (originalIndex != -1) {
            orginalItems.removeAt(originalIndex)
        }
        notifyItemRemoved(index)
    }
}
