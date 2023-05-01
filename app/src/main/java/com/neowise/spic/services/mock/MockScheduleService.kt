package com.neowise.spic.services.mock

import com.neowise.spic.model.EventDetail
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Schedule
import com.neowise.spic.model.Token
import com.neowise.spic.services.ScheduleService
import com.neowise.spic.util.DateRange

class MockScheduleService : ScheduleService {

    override fun getPlayerSchedule(id: Int, eventType: EventType, dateRange: DateRange): List<Schedule> {
        return (1..27).map { MockGenerator.generateSchedule(it) }
    }

    override fun getTrainerSchedule(id: Int, eventType: EventType, dateRange: DateRange): List<Schedule> {
        return (1..27).map { MockGenerator.generateSchedule(it) }
    }

    override fun getTeamSchedule(teamId: Int, eventType: EventType, dateRange: DateRange): List<Schedule> {
        return (1..27).map { MockGenerator.generateSchedule(it) }
    }

    override fun addSchedule(token: Token, teamId: Int, schedule: Schedule) {
    }

    override fun getScheduleDetails(scheduleId: Int): List<EventDetail> {
        return (1..15).map { MockGenerator.generateEventDetail() }
    }

    override fun updateScheduleDetails(details: List<EventDetail>) {
    }

    override fun updateScheduleDetail(detail: EventDetail) {
    }

    override fun deleteSchedule(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateSchedule(id: Int, schedule: Schedule) {
        TODO("Not yet implemented")
    }
}