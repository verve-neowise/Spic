package com.neowise.spic.services

import com.neowise.spic.model.Person
import com.neowise.spic.model.Team
import com.neowise.spic.model.TeamAttendance
import com.neowise.spic.model.Token
import com.neowise.spic.util.SimpleDate

interface TeamService {

    //get:team/players[team_id]
    fun getPlayers(teamId: Int): List<Person>

    //get:team/trainers[team_id]
    fun getTrainers(teamId: Int): List<Person>

    //get:team/attendance[team_id, date]
    fun getAttendance(teamId: Int, date: SimpleDate): TeamAttendance

    //get:player/team[player_id]
    fun getTeamByPlayer(playerId: Int): Team

    //get:trainer/teams[trainer_id]
    fun getTeamsByTrainer(trainerId: Int): List<Team>

    //#token
    //get:trainer/teams
    fun getTeamsByTrainerToken(token: Token): List<Team>

    //get:team{team_id}
    fun getTeamById(teamId: Int): Team

    //post:team[team]
    fun createTeam(team: Team)

    //put:team[team]
    fun updateTeam(teamId: Int, team: Team)

    //delete:team[team]
    fun removeTeam(teamId: Int)
}