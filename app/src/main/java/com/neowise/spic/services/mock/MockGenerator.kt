package com.neowise.spic.services.mock

import com.mooveit.library.Fakeit
import com.neowise.spic.model.*
import com.neowise.spic.util.SimpleDate
import com.neowise.spic.util.SimpleTime
import com.neowise.spic.util.TimeRange
import kotlin.math.roundToInt

object MockGenerator {

    init {
        Fakeit.init()
    }

    fun generateProfile() : Profile {
        return Profile(
            id = 0,
            name = name(),
            teamId = 1,
            photoUrl = largePick(),
            birthDay = SimpleDate.now(),
            emailAddress = email(),
            phoneNumber = "+1 (123) 456-65-48",
            gender = Gender.values().random(),
            team = team()
        )
    }

    fun generatePerson() : Person {
        return Person(
            id = 0,
            name = name(),
            phone = phone(),
            email = email(),
            photoUrl = smallPick(),
            role = Role.values().random()
        )
    }

    fun generateTeam(id: Int) : Team {
        return Team(
            id = id,
            code = "U-$id",
            name = "Team U-$id",
            activity = Fakeit.esports().game()
        )
    }

    fun generateEventDetail(): EventDetail {
        return EventDetail(
            id = 0,
            playerId = 0,
            eventId = 1,
            name = name(),
            photoUrl = smallPick(),
            behavior = StudentBehavior.values().random(),
            state = AttendanceState.values().random()
        )
    }

    fun generateSchedule(id: Int): Schedule {
        return Schedule(
            id = id,
            teamId = (1..58).random(),
            teamName = "U-${(1..58).random()}",
            name = name(),
            description = "",
            trainers = listOf(name(), name()).joinToString(),
            date = SimpleDate(2021, 5, id),
            time = TimeRange(SimpleTime.parse("15:00"), SimpleTime.parse("18:00")),
            attendance = AttendanceState.values().random(),
            behavior = StudentBehavior.values().random(),
            type = EventType.WORKOUT
        )
    }

    fun generateAttendance(): TeamAttendance {
        val range = (1..28)

        return TeamAttendance(
            dates = range.toList(),
            attendances = range.map { playerAttendance(range) }
        )
    }

    private fun playerAttendance(range: IntRange) : PlayerAttendance {

        val states = range.map { AttendanceState.values().random() }
        val attended = states.filter { it == AttendanceState.ATTENDED }.size
        val excused = states.filter { it == AttendanceState.EXCUSED }.size
        val late = states.filter { it == AttendanceState.LATE }.size

        val percent = (attended + late).toDouble()  / states.size.toDouble()

        return PlayerAttendance(
            name = Fakeit.name().name(),
            states = states,
            attendedCount = attended,
            excusedCount = excused,
            lateCount = late,
            percent = (percent * 100).roundToInt()
        )
    }

    fun phone() = "+" + (10000000000..90000000000).random()

    private fun team() = "Team U-" + (1..58).random()

    private fun name() = Fakeit.name().name()

    fun email() = Fakeit.name().firstName().toLowerCase() + "@google.com"

    private fun largePick() = "https://i.pravatar.cc/" + (250..300).random()

    private fun smallPick() = "https://i.pravatar.cc/" + (100..150).random()
}