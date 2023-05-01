package com.neowise.spic.services

import com.neowise.spic.model.EventDetail
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Schedule
import com.neowise.spic.model.Token
import com.neowise.spic.util.DateRange

interface ScheduleService {

    //get:schedule/player(type, dates)
    fun getPlayerSchedule(id: Int, eventType: EventType, dateRange: DateRange): List<Schedule>

    //get:schedule/trainer(type, dates)
    fun getTrainerSchedule(id: Int, eventType: EventType, dateRange: DateRange): List<Schedule>

    //get:schedule/team(team_id, dates)
    fun getTeamSchedule(teamId: Int, eventType: EventType, dateRange: DateRange): List<Schedule>

    //#token
    //post:schedule(schedule)
    fun addSchedule(token: Token, teamId: Int, schedule: Schedule)

    //get:schedule/details[shedule_id]
    fun getScheduleDetails(scheduleId: Int): List<EventDetail>

    //put:schedule/details[details]
    fun updateScheduleDetails(details: List<EventDetail>)

    //put:schedule/detail[detail]
    fun updateScheduleDetail(detail: EventDetail)

    //delete:schedule:id
    fun deleteSchedule(id: Int)

    //put:schedule:id
    fun updateSchedule(id: Int, schedule: Schedule)
}