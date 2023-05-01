package com.neowise.spic.services

interface ServiceFactory {
    val authService: AuthService
    val clubService: ClubService
    val profileService: ProfileService
    val scheduleService: ScheduleService
    val teamService: TeamService
}