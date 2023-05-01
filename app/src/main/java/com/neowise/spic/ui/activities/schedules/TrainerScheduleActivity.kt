package com.neowise.spic.ui.activities.schedules

import android.content.Intent
import android.widget.Toast
import com.neowise.spic.Const
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Schedule
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.EventDetailsActivity
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.Action
import com.neowise.spic.ui.adapters.holder.ActionCallback
import com.neowise.spic.ui.adapters.holder.ScheduleCallback
import com.neowise.spic.ui.adapters.holder.ScheduleHolderFactory
import com.neowise.spic.ui.dialogs.ActionDialog
import com.neowise.spic.ui.dialogs.EditEventDialog
import com.neowise.spic.ui.dialogs.EditEventDialog.EventDialogListener
import com.neowise.spic.ui.dialogs.TeamEditDialog.EditMethod
import com.neowise.spic.util.DateRange
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class TrainerScheduleActivity : AbstractScheduleActivity(), EventDialogListener, ScheduleCallback, ActionCallback {

    private var trainerId: Int = -1
    private var selectedSchedule: Schedule? = null

    override fun initialize() {
        adapter(ListAdapter(ScheduleHolderFactory(), this))
        trainerId = intent.getIntExtra(Const.TRAINER_ID, -1)
    }

    override fun fetchData(dateRange: DateRange, eventType: EventType) {

        RunIO<List<Schedule>>()
            .run {
                Services.scheduleService.getTrainerSchedule(trainerId, eventType, dateRange)
            }
            .onSuccess(::setItems)
            .onFailure { errorToast(applicationContext, it) }
            .execute()
    }

    override fun onEventEdit(schedule: Schedule, method: EditMethod) {
        val token = Preferences.instance(this).token

        if (method == EditMethod.CREATE) {
            RunIO<Unit>()
                .run {
                    Services.scheduleService.addSchedule(token, schedule.teamId, schedule)
                }
                .onSuccess {
                    Toast.makeText(this, "event successfuly added!", Toast.LENGTH_SHORT).show()
                    fetchData()
                }
                .onFailure { errorToast(applicationContext, it) }
                .execute()
        }
        else {
            RunIO<Unit>()
                .run {
                    Services.scheduleService.updateSchedule(schedule.id, schedule)
                }
                .onSuccess {
                    Toast.makeText(this, "event successfuly updated!", Toast.LENGTH_SHORT).show()
                    fetchData()
                }
                .onFailure { errorToast(applicationContext, it) }
                .execute()
        }
    }

    override fun onAddEventClicked() {
        EditEventDialog(trainerId).show(supportFragmentManager, "tag")
    }

    override fun onScheduleSelected(schedule: Schedule) {
        this.selectedSchedule = schedule

        ActionDialog(
            actions = listOf(Action.DETAILS, Action.EDIT, Action.REMOVE),
            callback = this
        ).show(supportFragmentManager, "action")
    }

    override fun onAction(action: Action) {
        selectedSchedule?.let {
            when(action) {
                Action.DETAILS -> {
                    val intent = Intent(this, EventDetailsActivity::class.java)
                    intent.putExtra(Const.SCHEDULE_ID, it.id)
                    intent.putExtra(Const.SCHEDULE_NAME, it.teamName + ": " + it.name)
                    intent.putExtra(Const.SCHEDULE_DATETIME, it.date.toString() + " | " + it.time.toString())
                    startActivity(intent)
                }
                Action.EDIT -> {
                    EditEventDialog(trainerId, it).show(supportFragmentManager, "tag")
                }
                Action.REMOVE -> {
                    RunIO<Unit>()
                        .run {
                            Services.scheduleService.deleteSchedule(it.id)
                        }
                        .onSuccess {
                            Toast.makeText(this, "event successfuly deleted!", Toast.LENGTH_SHORT).show()
                            fetchData()
                        }
                        .onFailure { errorToast(applicationContext, it) }
                        .execute()
                }
                else -> RuntimeException()
            }
        }
    }
}