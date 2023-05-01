package com.neowise.spic.ui.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class Holder<V, CB : Callback>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(value: V, callback: CB)
}