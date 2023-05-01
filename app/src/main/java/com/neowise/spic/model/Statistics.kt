package com.neowise.spic.model

data class Statistics(
    val scores: Double,
    val good: Int,
    val bad: Int,
    val attended: Int,
    val excused: Int,
    val late: Int,
    val events: Int,
    val workouts: Int,
) {
    companion object {
        val Empty = Statistics(0.0, 0, 0,0, 0, 0, 0, 0)
    }
}