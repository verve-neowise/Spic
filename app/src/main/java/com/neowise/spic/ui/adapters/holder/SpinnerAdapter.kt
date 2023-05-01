package com.neowise.spic.ui.adapters.holder

import android.content.Context
import android.widget.ArrayAdapter
import com.neowise.spic.R

class CustomWhiteAdapter<T>(context: Context, values: List<T>)
    : ArrayAdapter<T>(context, R.layout.layout_spinner_text_item_white, values)

class CustomSpinnerAdapter<T>(context: Context, values: List<T>)
    : ArrayAdapter<T>(context, R.layout.layout_spinner_text_item, values)