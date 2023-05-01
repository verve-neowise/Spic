package com.neowise.spic.model

import com.neowise.spic.util.SimpleDate
import com.neowise.spic.util.SimpleTime
import com.neowise.spic.util.TimeRange

data class Schedule(
    val id: Int,
    val teamId: Int,
    val teamName: String,
    val name: String,
    val description: String,
    val trainers: String,
    val date: SimpleDate,
    val time: TimeRange,
    val attendance: AttendanceState,
    val behavior: StudentBehavior,
    val type: EventType
) {
    companion object {

        val Empty = Schedule(
            -1,
            -1,
            "",
            "",
            "",
            "",
            SimpleDate(0, 0, 0),
            TimeRange(
                start = SimpleTime(0, 0),
                end = SimpleTime(0, 0)
            ),
            AttendanceState.LATE,
            StudentBehavior.POSITIVELY,
            EventType.ALL
        )
    }
}
