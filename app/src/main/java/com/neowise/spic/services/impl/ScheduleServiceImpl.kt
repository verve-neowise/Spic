package com.neowise.spic.services.impl

import com.neowise.spic.api.*
import com.neowise.spic.model.EventDetail
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Schedule
import com.neowise.spic.model.Token
import com.neowise.spic.services.*
import com.neowise.spic.util.DateRange
import com.neowise.spic.util.parseDetailList
import com.neowise.spic.util.parseScheduleList

class ScheduleServiceImpl(private val client: HttpClient) : ScheduleService {


    override fun getPlayerSchedule(id: Int, eventType: EventType, dateRange: DateRange): List<Schedule> {
        val start = dateRange.start.format()
        val end = dateRange.end.format()

        val url = "${API.schedulePlayer}/$id?type=${eventType.name}&start=$start&end=$end"
        val response = client.get(url)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseScheduleList(response.body()!!.string())
    }

    override fun getTrainerSchedule(id: Int, eventType: EventType, dateRange: DateRange): List<Schedule> {
        val start = dateRange.start.format()
        val end = dateRange.end.format()


        val url = "${API.scheduleTrainer}/$id?type=${eventType.name}&start=$start&end=$end"
        val response = client.get(url)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseScheduleList(response.body()!!.string())
    }

    override fun getTeamSchedule(teamId: Int, eventType: EventType, dateRange: DateRange): List<Schedule> {
        val start = dateRange.start.format()
        val end = dateRange.end.format()

        val url = "${API.scheduleTeam}/$teamId?type=${eventType.name}&start=$start&end=$end"
        val response = client.get(url)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseScheduleList(response.body()!!.string())
    }

    override fun addSchedule(token: Token, teamId: Int, schedule: Schedule) {
        val url = "${API.schedule}/$teamId"
        val response = client.post(url, schedule, token.token)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
            ResponseCode.Unauthorized -> throw ApiException("Unauthorized")
        }
    }

    override fun getScheduleDetails(scheduleId: Int): List<EventDetail> {
        val url = "${API.scheduleDetails}/$scheduleId"
        val response = client.get(url)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseDetailList(response.body()!!.string())
    }

    override fun updateScheduleDetails(details: List<EventDetail>) {
        val url = API.scheduleDetails
        val response = client.put(url, details)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun updateScheduleDetail(detail: EventDetail) {
        val url = API.scheduleDetail
        val response = client.put(url, detail)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun deleteSchedule(id: Int) {
        val url = "${API.schedule}/$id"
        val response = client.delete(url)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun updateSchedule(id: Int, schedule: Schedule) {
        val url = "${API.schedule}/$id"
        val response = client.put(url, schedule)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }
}