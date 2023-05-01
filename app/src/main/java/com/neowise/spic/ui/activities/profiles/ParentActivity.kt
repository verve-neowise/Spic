package com.neowise.spic.ui.activities.profiles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.databinding.ActivityParentBinding
import com.neowise.spic.model.ParentProfile
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.ContactsActivity
import com.neowise.spic.ui.activities.profiles.edit.ProfileEditActivity
import com.neowise.spic.ui.adapters.PersonAdapter
import com.neowise.spic.ui.dialogs.PlayerOverviewDialog
import com.neowise.spic.util.RunIO
import com.neowise.spic.Const
import com.neowise.spic.util.loadImage

class ParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParentBinding
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personAdapter = PersonAdapter {
            PlayerOverviewDialog(it).show(supportFragmentManager, "tag")
        }

        binding.personList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personAdapter
        }

        binding.profileMenu.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivityForResult(intent, Const.UPDATE_DATA)
        }

        binding.contactsMenu.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }
        refreshData()
    }

    private fun refreshData() {
        binding.container.visibility = View.INVISIBLE
        binding.swipeRefresh.post { binding.swipeRefresh.isRefreshing = true }
        fetchData()
    }

    private fun fetchData() {
        val token = Preferences.instance(this).token

        RunIO<ParentProfile>()
            .run {
                val profile = Services.profileService.getProfile(token)
                val children = Services.profileService.parentChildren(profile.id)
                ParentProfile(profile, children)
            }
            .onSuccess(::fillEntries)
            .onFailure { throw it }
            .execute()
    }

    private fun fillEntries(parent: ParentProfile) {
        binding.name.text = parent.profile.name
        binding.birthDate.text = parent.profile.birthDay.toString()
        personAdapter.items = parent.children

        loadImage(this, parent.profile.id, binding.profilePhoto)

        binding.swipeRefresh.isRefreshing = false
        binding.container.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.UPDATE_DATA) {
            refreshData()
        }
    }
}