package com.neowise.spic.ui.activities.team

import com.neowise.spic.R
import com.neowise.spic.Const
import com.neowise.spic.model.Person
import com.neowise.spic.model.Token
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.PersonListActivity
import com.neowise.spic.ui.dialogs.ProfileOverviewDialog

class TeamPlayersActivity : PersonListActivity(R.string.players) {

    private lateinit var token: Token
    private var teamId: Int = 0

    override fun initialize() {

        token = Preferences.instance(this).token
        teamId = intent.getIntExtra(Const.TEAM_ID, -1)

        enableSearching(false)

        super.initialize()
    }

    override fun fetchData(): List<Person> {
        return Services.teamService.getPlayers(teamId)
    }

    override fun onPersonSelected(person: Person, position: Int) {
        ProfileOverviewDialog(person).show(supportFragmentManager, "profile")
    }
}