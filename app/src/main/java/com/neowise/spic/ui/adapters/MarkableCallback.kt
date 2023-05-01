package com.neowise.spic.ui.adapters

import com.neowise.spic.ui.adapters.base.Callback

interface Markable {
    var marked: Boolean
}

interface MarkableCallback<T> : Callback {
    fun markableStateChanged(markable: T, position: Int)
}