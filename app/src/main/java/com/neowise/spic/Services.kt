package com.neowise.spic

import com.neowise.spic.services.*
import com.neowise.spic.services.mock.MockServiceFactory

object Services {

    private val serviceFactory: ServiceFactory = MockServiceFactory()

    val authService: AuthService = serviceFactory.authService
    val clubService: ClubService = serviceFactory.clubService
    val profileService: ProfileService = serviceFactory.profileService
    val teamService: TeamService = serviceFactory.teamService
    val scheduleService: ScheduleService = serviceFactory.scheduleService
}