package com.neowise.spic.services.impl

import com.neowise.spic.api.*
import com.neowise.spic.model.Person
import com.neowise.spic.model.Profile
import com.neowise.spic.model.Statistics
import com.neowise.spic.model.Token
import com.neowise.spic.services.*
import com.neowise.spic.util.parse
import com.neowise.spic.util.parsePersonList

class ProfileServiceImpl(private val client: HttpClient) : ProfileService {

    override fun getProfile(token: Token): Profile {
        val response = client.get(API.profile, token.token)
        when(response.code()) {
            ResponseCode.Unauthorized -> throw ApiException("Unauthorized.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(response.body()!!.string(), Profile::class.java)
    }

    override fun updateProfile(token: Token, profile: Profile) {
        val response = client.put(API.profile, profile, token.token)
        when(response.code()) {
            ResponseCode.Unauthorized -> throw ApiException("Unauthorized.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun changePhoto(token: Token, photoUrl: String) {
        val url = "${API.profilePhoto}?url=$photoUrl"
        val response = client.put(url = url, token = token.token)
        when(response.code()) {
            ResponseCode.Unauthorized -> throw ApiException("Unauthorized.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
    }

    override fun getStatistics(profileId: Int): Statistics {
        val url = "${API.playerStatistics}/$profileId"
        val response = client.get(url)
        when(response.code()) {
            ResponseCode.Unauthorized -> throw ApiException("Unauthorized.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(response.body()!!.string(), Statistics::class.java)
    }

    override fun parentChildren(parentId: Int): List<Person> {
        val response = client.get("${API.parentChildren}/$parentId")
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parsePersonList(body)
    }
}