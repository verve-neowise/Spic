package com.neowise.spic.ui.activities.schedules

import android.content.Context
import android.content.Intent
import com.neowise.spic.Const

fun Context.startPlayerSchedule(playerId: Int) {
    val intent = Intent(this, PlayerScheduleActivity::class.java)
    intent.putExtra(Const.PLAYER_ID, playerId)
    startActivity(intent)
}

fun Context.startTrainerSchedule(trainerId: Int) {
    val intent = Intent(this, TrainerScheduleActivity::class.java)
    intent.putExtra(Const.TRAINER_ID, trainerId)
    startActivity(intent)
}

fun Context.startTeamSchedule(teamId: Int) {
    val intent = Intent(this, TeamScheduleActivity::class.java)
    intent.putExtra(Const.TEAM_ID, teamId)
    startActivity(intent)
}