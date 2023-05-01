package com.neowise.spic.ui.activities.profiles

import android.content.Intent
import android.view.View
import com.neowise.spic.model.PlayerProfile
import com.neowise.spic.model.Profile
import com.neowise.spic.model.Statistics
import com.neowise.spic.model.Team
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.ContactsActivity
import com.neowise.spic.ui.activities.base.ProfileActivity
import com.neowise.spic.ui.activities.profiles.edit.ProfileEditActivity
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.ui.activities.schedules.startPlayerSchedule
import com.neowise.spic.ui.dialogs.StatisticsDialog
import com.neowise.spic.ui.dialogs.TeamOverviewDialog
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO
import com.neowise.spic.Const
import com.neowise.spic.util.loadImage

class PlayerActivity : ProfileActivity() {

    private var profile: Profile = Profile.Empty
    private var statistics: Statistics = Statistics.Empty
    private var team: Team = Team.Empty

    override fun initialize() {

        addMenu(Menu.SCHEDULE) {
            startPlayerSchedule(profile.id)
        }
        addMenu(Menu.TEAM) {
            if (team != Team.Empty) {
                TeamOverviewDialog(team).show(supportFragmentManager, "team")
            }
        }
        addMenu(Menu.STATISTICS) {
            if (profile != Profile.Empty) {
                StatisticsDialog(profile.id)
                    .show(supportFragmentManager, "tag")
            }
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

        RunIO<PlayerProfile>()
            .run {
                val player = Services.profileService.getProfile(token)
                val team = Services.teamService.getTeamByPlayer(player.id)
                val statistics = Services.profileService.getStatistics(player.id)

                PlayerProfile(player, team, statistics)
            }
            .onSuccess(::fillEntries)
            .onFailure { errorToast(this, it) }
            .execute()
    }

    private fun fillEntries(player: PlayerProfile) {

        binding.name.text = player.profile.name
        binding.birthDate.text = player.profile.birthDay.toString()
        binding.teamName.text = player.team.name

        this.team = player.team
        this.statistics = player.statistics
        this.profile = player.profile

        loadImage(this, player.profile.id, binding.profilePhoto)

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