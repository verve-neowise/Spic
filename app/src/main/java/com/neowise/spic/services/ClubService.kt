package com.neowise.spic.services

import com.neowise.spic.model.*

interface ClubService {

    // get:club/players
    fun getPlayers(): List<Person>

    // get:club/trainers
    fun getTrainers(): List<Person>

    // get:club/parents
    fun getParents(): List<Person>

    // get:club/teams
    fun getTeams(): List<Team>

    // get:club/allUsers
    fun getAllUsers(): List<User>

    fun deleteUser(userId: Int)

    // post:trainer/team[trainer_id, team_id]
    fun addTeamToTrainer(trainerId: Int, teamId: Int)

    // delete:trainer/team[trainer_id, team_id]
    fun removeTeamFromTrainer(trainerId: Int, teamId: Int)

    // put:player/team[trainer_id, team_id]
    fun changePlayerTeam(playerId: Int, teamId: Int)

    // put:club/role[user_id, role]
    fun changeUserRole(userId: Int, role: Role)

    // post:parent/child[parent_id, player_id]
    fun addChildToParent(parentId: Int, playerId: Int)

    // delete:parent/child[parent_id, player_id]
    fun removeChildFromParent(parentId: Int, playerId: Int)

    // get:club/contacts
    fun getContacts(): Contacts

    // put:club/contacts[contacts]
    fun updateContacts(contacts: Contacts)
}