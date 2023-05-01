package com.neowise.spic.ui.activities.profiles

import android.content.Intent
import android.view.View
import com.neowise.spic.Const
import com.neowise.spic.model.Profile
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.ContactsActivity
import com.neowise.spic.ui.activities.base.ProfileActivity
import com.neowise.spic.ui.activities.profiles.edit.ProfileEditActivity
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.ui.activities.schedules.startTrainerSchedule
import com.neowise.spic.ui.activities.trainer.TrainerTeamsActivity
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO
import com.neowise.spic.util.loadImage

class TrainerActivity : ProfileActivity() {

    private var trainerId = -1

    override fun initialize() {
        addMenu(Menu.SCHEDULE) {
            startTrainerSchedule(trainerId)
        }
        addMenu(Menu.TEAMS) {
            val intent = Intent(this, TrainerTeamsActivity::class.java)
            intent.putExtra(Const.TRAINER_ID, trainerId)
            startActivity(intent)
        }
        addMenu(Menu.PROFILE) {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivityForResult(intent, Const.UPDATE_DATA)
        }
        addMenu(Menu.CONTACTS) {
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
        RunIO<Profile>()
            .run {
                Services.profileService.getProfile(token)
            }
            .onSuccess(::fillEntries)
            .onFailure { errorToast(this, it) }
            .execute()
    }

    private fun fillEntries(profile: Profile) {
        trainerId = profile.id

        binding.name.text = profile.name
        binding.birthDate.text = profile.birthDay.toString()

        loadImage(this, profile.id, binding.profilePhoto)

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