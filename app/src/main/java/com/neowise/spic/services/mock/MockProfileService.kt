package com.neowise.spic.services.mock

import com.neowise.spic.model.Person
import com.neowise.spic.model.Profile
import com.neowise.spic.model.Statistics
import com.neowise.spic.model.Token
import com.neowise.spic.services.ProfileService

class MockProfileService : ProfileService {

    override fun getProfile(token: Token): Profile {
        return MockGenerator.generateProfile()
    }

    override fun updateProfile(token: Token, profile: Profile) {
    }

    override fun changePhoto(token: Token, photoUrl: String) {
    }

    override fun getStatistics(profileId: Int): Statistics {
        return Statistics(5.6, 14, 16, 18, 5, 7, 30, 0)
    }

    override fun parentChildren(parentId: Int): List<Person> {
        return (1..3).map { MockGenerator.generatePerson() }
    }
}