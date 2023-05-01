package com.neowise.spic.model

data class TeamAttendance(
    val dates: List<Int>,
    val attendances: List<PlayerAttendance>
) {
    companion object {
        val Empty = TeamAttendance(emptyList(), emptyList())
    }
}