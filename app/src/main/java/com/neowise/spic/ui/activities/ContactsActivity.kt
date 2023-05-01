package com.neowise.spic.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.neowise.spic.model.Contacts
import com.neowise.spic.databinding.ActivityContactsBinding
import com.neowise.spic.Services
import com.neowise.spic.util.phoneCall
import com.neowise.spic.util.sendEmail
import com.neowise.spic.util.RunIO

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private var contacts: Contacts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsBinding.inflate(layoutInflater)

        binding.clubEmail.setOnClickListener {
            sendEmail()
        }
        binding.clubPhone.setOnClickListener {
            phoneCall()
        }

        setContentView(binding.root)

        binding.swipeRefresh.setOnRefreshListener {
            fetchData()
        }
        fetchData()
    }

    private fun sendEmail() {
        contacts?.let {
            this.sendEmail(it.email)
        }
    }

    private fun phoneCall() {
        contacts?.let {
            this.phoneCall(it.phone)
        }
    }

    private fun fetchData() {
        binding.container.visibility = View.INVISIBLE
        binding.swipeRefresh.post { binding.swipeRefresh.isRefreshing = true }

        RunIO<Contacts>()
            .run { Services.clubService.getContacts() }
            .onSuccess {
                this.contacts = it

                binding.clubEmailText.text = it.email
                binding.clubPhoneText.text = it.phone
                binding.description.text = it.description

                binding.container.visibility = View.VISIBLE
                binding.swipeRefresh.isRefreshing = false
            }
            .execute()
    }
}