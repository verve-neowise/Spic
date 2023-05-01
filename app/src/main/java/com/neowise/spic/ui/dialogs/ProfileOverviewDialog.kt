package com.neowise.spic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.R
import com.neowise.spic.databinding.DialogProfileOverviewBinding
import com.neowise.spic.model.Person
import com.neowise.spic.util.loadImage
import com.neowise.spic.util.phoneCall
import com.neowise.spic.util.sendEmail

class ProfileOverviewDialog(val person: Person) : BottomSheetDialogFragment(){

    private lateinit var binding: DialogProfileOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogProfileOverviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.text = person.name
        binding.phoneNumber.text = getString(R.string.phoneNumber) + ": " + person.phone
        binding.phoneNumber.setOnClickListener { phoneCall() }

        binding.emailAddress.text = getString(R.string.email) + ": " + person.email
        binding.emailAddress.setOnClickListener { sendEmail() }

        loadImage(context!!, person.id, binding.profilePhoto)
    }

    private fun sendEmail() {
        context?.sendEmail(person.email)
    }

    private fun phoneCall() {
        context?.phoneCall(person.phone)
    }

}