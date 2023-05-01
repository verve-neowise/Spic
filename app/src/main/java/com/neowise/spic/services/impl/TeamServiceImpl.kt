package com.neowise.spic.services.impl

import com.neowise.spic.api.*
import com.neowise.spic.model.Person
import com.neowise.spic.model.Team
import com.neowise.spic.model.TeamAttendance
import com.neowise.spic.model.Token
import com.neowise.spic.services.*
import com.neowise.spic.util.SimpleDate
import com.neowise.spic.util.parse
import com.neowise.spic.util.parsePersonList
import com.neowise.spic.util.parseTeamList

class TeamServiceImpl(private val client: HttpClient) : TeamService {

    override fun getPlayers(teamId: Int): List<Person> {
        val response = client.get("${API.teamPlayers}/$teamId")
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parsePersonList(response.body()!!.string())
    }

    override fun getTrainers(teamId: Int): List<Person> {
        val response = client.get("${API.teamTrainers}/$teamId")
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parsePersonList(response.body()!!.string())
    }

    override fun getAttendance(teamId: Int, date: SimpleDate): TeamAttendance {
        val start = date.copy(day = 1).format()
        val end = date.copy(day = 31).format()
        val response = client.get("${API.teamAttendance}/$teamId?start=$start&end=$end")
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(response.body()!!.string(), TeamAttendance::class.java)
    }

    override fun getTeamByPlayer(playerId: Int): Team {
        val response = client.get("${API.playerTeam}/$playerId")
        when(response.code()) {
            ResponseCode.NotFound -> return Team.Empty
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(response.body()!!.string(), Team::class.java)
    }

    override fun getTeamsByTrainer(trainerId: Int): List<Team> {
        val response = client.get("${API.trainerTeams}/$trainerId")
        when(response.code()) {
            ResponseCode.NotFound -> throw ApiException("Teams not found.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseTeamList(response.body()!!.string())
    }

    override fun getTeamsByTrainerToken(token: Token): List<Team> {
        TODO("Not yet implemented")
    }

    //get:team{team_id}
    override fun getTeamById(teamId: Int): Team {
        val response = client.get("${API.team}/$teamId")
        when(response.code()) {
            ResponseCode.NotFound -> throw ApiException("Team not found.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(response.body()!!.string(), Team::class.java)
    }

    //post:team[team]
    override fun createTeam(team: Team) {
        val response = client.post(API.team, team)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    //put:team[team]
    override fun updateTeam(teamId: Int, team: Team) {
        val response = client.put("${API.team}/$teamId", team)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    //delete:team[team]
    override fun removeTeam(teamId: Int) {
        val response = client.delete("${API.team}/$teamId")
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }
}