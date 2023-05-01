package com.neowise.spic.services.impl
import com.neowise.spic.api.*
import com.neowise.spic.model.*
import com.neowise.spic.services.*
import com.neowise.spic.util.parse
import com.neowise.spic.util.parsePersonList
import com.neowise.spic.util.parseTeamList
import com.neowise.spic.util.parseUserList

class ClubServiceImpl(private val client: HttpClient) : ClubService {

    override fun getPlayers(): List<Person> {
        val response = client.get(API.clubPlayers)
        val body = response.body()!!.string()

        println(body)

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parsePersonList(body)
    }

    override fun getTrainers(): List<Person> {
        val response = client.get(API.clubTrainers)
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parsePersonList(body)
    }

    override fun getParents(): List<Person> {
        val response = client.get(API.clubParents)
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parsePersonList(body)
    }

    override fun getTeams(): List<Team> {
        val response = client.get(API.clubTeams)
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseTeamList(body)
    }

    override fun getAllUsers(): List<User> {
        val response = client.get(API.clubAllUsers)
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parseUserList(body)
    }

    override fun deleteUser(id: Int) {
        val response = client.delete("${API.clubAllUsers}/$id")
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun addTeamToTrainer(trainerId: Int, teamId: Int) {
        val url = "${API.trainerTeam}?trainer_id=$trainerId&team_id=$teamId"
        val response = client.post(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun removeTeamFromTrainer(trainerId: Int, teamId: Int) {
        val url = "${API.trainerTeam}?trainer_id=$trainerId&team_id=$teamId"
        val response = client.delete(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun changePlayerTeam(playerId: Int, teamId: Int) {
        val url = "${API.playerTeam}?player_id=$playerId&team_id=$teamId"
        val response = client.put(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun changeUserRole(userId: Int, role: Role) {
        val url = "${API.clubRole}/$userId?role=${role.name}"
        val response = client.put(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }
    // post:parent/child[parent_id, player_id]
    override fun addChildToParent(parentId: Int, playerId: Int) {
        val url = "${API.parentChild}?parent_id=$parentId&child_id=$playerId"
        val response = client.put(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun removeChildFromParent(parentId: Int, playerId: Int) {
        val url = "${API.parentChild}?parent_id=$parentId&child_id=$playerId"
        val response = client.delete(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun getContacts(): Contacts {
        val url = API.clubContacts
        val response = client.get(url)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(response.body()!!.string(), Contacts::class.java)
    }

    override fun updateContacts(contacts: Contacts) {
        val url = API.clubContacts
        val response = client.put(url, contacts)
        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }
}