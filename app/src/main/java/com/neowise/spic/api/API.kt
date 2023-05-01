package com.neowise.spic.api

object API {
    private const val base = "{base_url}"
    private const val club = "$base/club"
    private const val trainer = "$base/trainer"
    private const val player = "$base/player"
    private const val parent = "$base/parent"
    const val team = "$base/team"
    const val schedule = "$base/schedule"
    const val profile = "$base/profile"

    const val login = "$base/login"
    const val signup = "$base/signup"
    const val clubPlayers = "$club/players"
    const val clubTrainers = "$club/trainers"
    const val clubParents = "$club/parents"
    const val clubAllUsers = "$club/users"
    const val clubTeams = "$club/teams"
    const val clubRole = "$club/role"
    const val clubContacts = "$club/contacts"

    const val trainerTeam = "$trainer/team"
    const val trainerTeams = "$trainer/teams"
    const val playerTeam = "$player/team"
    const val playerStatistics = "$player/statistics"

    const val parentChild = "$parent/child"
    const val parentChildren = "$parent/children"

    const val profilePhoto = "$profile/photo"

    const val schedulePlayer = "$schedule/player"
    const val scheduleTrainer = "$schedule/trainer"
    const val scheduleTeam = "$schedule/team"
    const val scheduleDetails = "$schedule/details"
    const val scheduleDetail = "$schedule/detail"

    const val teamPlayers = "$team/players"
    const val teamTrainers = "$team/trainers"
    const val teamAttendance = "$team/attendance"
}