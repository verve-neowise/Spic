package com.neowise.spic.services.mock

import com.neowise.spic.model.*
import com.neowise.spic.services.ClubService

class MockClubService : ClubService{

    override fun getPlayers(): List<Person> {
        return (1..20).map { MockGenerator.generatePerson() }
    }

    override fun getTrainers(): List<Person> {
        return (1..20).map { MockGenerator.generatePerson() }
    }

    override fun getParents(): List<Person> {
        return (1..20).map { MockGenerator.generatePerson() }
    }

    override fun getTeams(): List<Team> {
        return (1..20).map { MockGenerator.generateTeam(it) }
    }

    override fun getAllUsers(): List<User> {
        return listOf(User.Empty)
    }

    override fun deleteUser(userId: Int) {
    }

    override fun addTeamToTrainer(trainerId: Int, teamId: Int) {
    }

    override fun removeTeamFromTrainer(trainerId: Int, teamId: Int) {
    }

    override fun changePlayerTeam(playerId: Int, teamId: Int) {
    }

    override fun changeUserRole(userId: Int, role: Role) {
    }

    override fun addChildToParent(parentId: Int, playerId: Int) {
    }

    override fun removeChildFromParent(parentId: Int, playerId: Int) {
    }

    override fun getContacts(): Contacts {
        return Contacts(email = MockGenerator.email(), phone = MockGenerator.phone(), "Some Adress")
    }

    override fun updateContacts(contacts: Contacts) {
    }
}