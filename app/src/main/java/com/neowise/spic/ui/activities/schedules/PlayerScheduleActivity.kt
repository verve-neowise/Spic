package com.neowise.spic.ui.activities.schedules

import android.view.View
import com.neowise.spic.Const
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Schedule
import com.neowise.spic.Services
import com.neowise.spic.ui.adapters.base.Empty
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.PlayerScheduleHolderFactory
import com.neowise.spic.util.DateRange
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class PlayerScheduleActivity : AbstractScheduleActivity() {

    private var playerId = -1

    override fun initialize() {
        adapter(ListAdapter(PlayerScheduleHolderFactory(), Empty))
        binding.addEventButton.visibility = View.GONE
        playerId = intent.getIntExtra(Const.PLAYER_ID, -1)
    }

    override fun fetchData(dateRange: DateRange, eventType: EventType) {

        RunIO<List<Schedule>>()
            .run {
                Services.scheduleService.getPlayerSchedule(playerId, eventType, dateRange)
            }
            .onSuccess(::setItems)
            .onFailure { errorToast(applicationContext, it) }
            .execute()
    }
}