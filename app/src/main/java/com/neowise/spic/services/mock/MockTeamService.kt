package com.neowise.spic.services.mock

import com.neowise.spic.model.Person
import com.neowise.spic.model.Team
import com.neowise.spic.model.TeamAttendance
import com.neowise.spic.model.Token
import com.neowise.spic.services.TeamService
import com.neowise.spic.util.SimpleDate

class MockTeamService : TeamService {
    override fun getPlayers(teamId: Int): List<Person> {
        return (1..15).map { MockGenerator.generatePerson() }
    }

    override fun getTrainers(teamId: Int): List<Person> {
        return (1..5).map { MockGenerator.generatePerson() }
    }

    override fun getAttendance(teamId: Int, date: SimpleDate): TeamAttendance {
        return MockGenerator.generateAttendance()
    }

    override fun getTeamById(teamId: Int): Team {
        return MockGenerator.generateTeam(teamId)
    }

    override fun getTeamByPlayer(playerId: Int): Team {
        return MockGenerator.generateTeam(playerId)
    }

    override fun getTeamsByTrainer(trainerId: Int): List<Team> {
        return (1..5).map { MockGenerator.generateTeam(it) }
    }

    override fun getTeamsByTrainerToken(token: Token): List<Team> {
        return (1..5).map { MockGenerator.generateTeam(it) }
    }

    override fun createTeam(team: Team) {
    }

    override fun removeTeam(teamId: Int) {
    }

    override fun updateTeam(teamId: Int, team: Team) {
    }
}