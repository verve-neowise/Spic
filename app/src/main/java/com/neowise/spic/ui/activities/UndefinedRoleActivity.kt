package com.neowise.spic.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neowise.spic.databinding.ActivityUndefinedRoleBinding

class UndefinedRoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUndefinedRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUndefinedRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contactsButton.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }
    }
}