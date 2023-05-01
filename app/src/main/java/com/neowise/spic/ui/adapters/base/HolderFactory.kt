package com.neowise.spic.ui.adapters.base

import android.view.LayoutInflater
import android.view.ViewGroup

interface HolderFactory<V, CB : Callback> {
    fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<V, CB>
}

