package com.neowise.spic.model

class PlayerAttendance(
    val name: String,
    val percent: Int,
    val attendedCount: Int,
    val excusedCount: Int,
    val lateCount: Int,
    val states: List<AttendanceState>
)