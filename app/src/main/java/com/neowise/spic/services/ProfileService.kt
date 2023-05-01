package com.neowise.spic.services

import com.neowise.spic.model.Person
import com.neowise.spic.model.Profile
import com.neowise.spic.model.Statistics
import com.neowise.spic.model.Token

interface ProfileService {

    // #token
    // get:/profile
    fun getProfile(token: Token): Profile

    // #token
    // put:/profile[profile]
    fun updateProfile(token: Token, profile: Profile)

    // #token
    // put:/profile/photo[photoUrl]
    fun changePhoto(token: Token, photoUrl: String)

    // get:/player/statistics/{profileId}
    fun getStatistics(profileId: Int): Statistics

    // get:/parent/children/{parentId}
    fun parentChildren(parentId: Int) : List<Person>
}
