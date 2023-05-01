package com.neowise.spic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.databinding.DialogActionsBinding
import com.neowise.spic.ui.adapters.holder.Action
import com.neowise.spic.ui.adapters.holder.ActionAdapter
import com.neowise.spic.ui.adapters.holder.ActionCallback

class ActionDialog(val actions: List<Action>, val callback: ActionCallback) : BottomSheetDialogFragment(), ActionCallback {

    private lateinit var binding: DialogActionsBinding
    private lateinit var actionAdapter: ActionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionAdapter = ActionAdapter(this)
        binding = DialogActionsBinding.inflate(inflater)
        return binding.root
    }

    fun title(title: Int) {
        binding.title.setText(title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionList.adapter = actionAdapter
        binding.actionList.layoutManager = LinearLayoutManager(context!!)
        actionAdapter.setItems(actions)
    }

    override fun onAction(action: Action) {
        callback.onAction(action)
        dismiss()
    }
}