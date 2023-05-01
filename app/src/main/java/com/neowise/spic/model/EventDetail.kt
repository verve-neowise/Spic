package com.neowise.spic.model

data class EventDetail(
    val id: Int,
    val eventId: Int,
    val playerId: Int,
    val name: String,
    val photoUrl: String,
    val behavior: StudentBehavior,
    val state: AttendanceState
)