package com.neowise.spic.ui.activities.profiles

import android.content.Intent
import android.view.View
import com.neowise.spic.Const
import com.neowise.spic.model.Contacts
import com.neowise.spic.model.ClubProfile
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.ProfileActivity
import com.neowise.spic.ui.activities.club.*
import com.neowise.spic.ui.activities.profiles.edit.ProfileEditActivity
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO
import com.neowise.spic.util.loadImage

class ClubActivity : ProfileActivity() {

    private var contacts: Contacts? = null

    override fun initialize() {
        binding.teamName.visibility = View.GONE

        addMenu(Menu.TEAMS) {
            startActivity(Intent(this, ClubTeamsActivity::class.java))
        }
        addMenu(Menu.PLAYERS) {
            startActivity(Intent(this, ClubPlayersActivity::class.java))
        }
        addMenu(Menu.TRAINERS) {
            startActivity(Intent(this, ClubTrainersActivity::class.java))
        }
        addMenu(Menu.PARENTS) {
            startActivity(Intent(this, ParentsActivity::class.java))
        }
        addMenu(Menu.ALL_USERS) {
            startActivity(Intent(this, ClubUsersActivity::class.java))
        }
        addMenu(Menu.PROFILE) {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivityForResult(intent, Const.UPDATE_DATA)
        }
        addMenu(Menu.CONTACTS) {
            if (contacts != null) {
                UpdateContactsDialog(contacts!!, ::fetchData).show(supportFragmentManager, "contacts")
            }
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

        RunIO<ClubProfile>()
            .run {
                val profile = Services.profileService.getProfile(token)
                val contacts = Services.clubService.getContacts()

                ClubProfile(profile, contacts)
            }
            .onSuccess(::fillEntries)
            .onFailure { errorToast(this, it) }
            .execute()
    }

    private fun fillEntries(club: ClubProfile) {
        binding.name.text = club.profile.name
        binding.birthDate.text = club.profile.birthDay.toString()

        contacts = club.contacts
        loadImage(this, club.profile.id, binding.profilePhoto)

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