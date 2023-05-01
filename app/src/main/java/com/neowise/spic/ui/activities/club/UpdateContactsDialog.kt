package com.neowise.spic.ui.activities.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.model.Contacts
import com.neowise.spic.databinding.DialogContactEditBinding
import com.neowise.spic.Services
import com.neowise.spic.util.buildModel
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.validateValue
import com.neowise.spic.util.RunIO

class UpdateContactsDialog(val contacts: Contacts, val callback: () -> Unit) : BottomSheetDialogFragment(){

    private lateinit var binding: DialogContactEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogContactEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailAddress.setText(contacts.email)
        binding.phoneNumber.setText(contacts.phone)
        binding.description.setText(contacts.description)

        binding.saveChangesButton.setOnClickListener {
            sendUpdates()
        }
    }

    private fun sendUpdates() {

        val model = buildModel(context!!, Contacts.Empty) {

            val email = binding.emailAddress.validateValue("email")
            val phone = binding.phoneNumber.validateValue("phone")
            val description = binding.description.text.toString()

            Contacts(email, phone, description)
        }

        if (model == Contacts.Empty) {
            return
        }

        RunIO<Unit>()
            .run {
                Services.clubService.updateContacts(model)
            }
            .onSuccess {
                Toast.makeText(context, "Contact has been updated!", Toast.LENGTH_SHORT).show()
                callback()
                dismiss()
            }
            .onFailure { errorToast(context!!, it) }
            .execute()
    }
}