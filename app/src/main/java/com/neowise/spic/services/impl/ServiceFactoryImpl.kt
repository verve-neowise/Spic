package com.neowise.spic.services.impl

import com.neowise.spic.api.HttpClient
import com.neowise.spic.services.*

class ServiceFactoryImpl : ServiceFactory {

    private val client = HttpClient()

    override val authService: AuthService
        by lazy { AuthServiceImpl(client) }

    override val clubService: ClubService
            by lazy { ClubServiceImpl(client) }

    override val profileService: ProfileService
            by lazy { ProfileServiceImpl(client) }

    override val scheduleService: ScheduleService
            by lazy { ScheduleServiceImpl(client) }

    override val teamService: TeamService
            by lazy { TeamServiceImpl(client) }
}