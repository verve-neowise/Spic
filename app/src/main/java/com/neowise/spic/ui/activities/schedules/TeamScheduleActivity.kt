package com.neowise.spic.ui.activities.schedules

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.neowise.spic.Const
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Role
import com.neowise.spic.model.Schedule
import com.neowise.spic.model.Token
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.EventDetailsActivity
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.ScheduleCallback
import com.neowise.spic.ui.adapters.holder.ScheduleHolderFactory
import com.neowise.spic.ui.dialogs.EditEventDialog
import com.neowise.spic.ui.dialogs.EditEventDialog.EventDialogListener
import com.neowise.spic.ui.dialogs.TeamEditDialog
import com.neowise.spic.util.DateRange
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class TeamScheduleActivity : AbstractScheduleActivity(), EventDialogListener, ScheduleCallback {

    private lateinit var teamName: String
    private lateinit var token: Token
    private var trainerId = -1
    private var teamId = -1

    override fun initialize() {
        adapter(ListAdapter(ScheduleHolderFactory(), this))
        token = Preferences.instance(this).token
        trainerId = intent.getIntExtra(Const.TRAINER_ID, 0)
        teamId = intent.getIntExtra(Const.TEAM_ID, 0)
        teamName = intent.getStringExtra(Const.TEAM_NAME)!!

        if (token.role != Role.TRAINER) {
            binding.addEventButton.visibility = View.GONE
        }
    }

    override fun fetchData(dateRange: DateRange, eventType: EventType) {

        RunIO<List<Schedule>>()
            .run {
                Services.scheduleService.getTeamSchedule(teamId, eventType, dateRange)
            }
            .onSuccess(::setItems)
            .onFailure { errorToast(applicationContext, it) }
            .execute()
    }

    override fun onEventEdit(schedule: Schedule, mode: TeamEditDialog.EditMethod) {
        RunIO<Unit>()
            .run {
                Services.scheduleService.addSchedule(token, teamId, schedule)
            }
            .onSuccess {
                Toast.makeText(this, "event successfuly added!", Toast.LENGTH_SHORT).show()
                fetchData()
            }
            .onFailure { errorToast(applicationContext, it) }
            .execute()
    }

    override fun onAddEventClicked() {

        val teams = if (token.role == Role.TRAINER) {
            Services.teamService.getTeamsByTrainer(trainerId)
        }
        else {
            Services.clubService.getTeams()
        }

        val selectedTeam = teams.find { it.id == teamId } ?: teams.first()

        EditEventDialog(trainerId, Schedule.Empty, selectedTeam).show(supportFragmentManager, "tag")
    }

    override fun onScheduleSelected(schedule: Schedule) {
        if (token.role == Role.TRAINER) {
            val intent = Intent(this, EventDetailsActivity::class.java)
            intent.putExtra(Const.SCHEDULE_ID, schedule.id)
            intent.putExtra(Const.SCHEDULE_NAME, schedule.teamName + ": " + schedule.name)
            intent.putExtra(Const.SCHEDULE_DATETIME, schedule.date.toString() + " | " + schedule.time.toString())
            startActivity(intent)
        }
    }
}