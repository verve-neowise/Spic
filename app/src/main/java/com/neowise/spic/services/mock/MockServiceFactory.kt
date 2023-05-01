package com.neowise.spic.services.mock

import com.neowise.spic.services.*

class MockServiceFactory : ServiceFactory {

    override val authService: AuthService
        by lazy { MockAuthService() }

    override val clubService: ClubService
            by lazy { MockClubService() }

    override val profileService: ProfileService
            by lazy { MockProfileService() }

    override val scheduleService: ScheduleService
            by lazy { MockScheduleService() }

    override val teamService: TeamService
            by lazy { MockTeamService() }
}