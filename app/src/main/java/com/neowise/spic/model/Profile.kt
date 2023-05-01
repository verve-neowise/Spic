package com.neowise.spic.model

import com.neowise.spic.util.SimpleDate

data class Profile(

    val id: Int,
    val name: String,
    val birthDay: SimpleDate,
    val gender: Gender,
    val phoneNumber: String,
    val emailAddress: String,
    val photoUrl: String,
    val team: String,
    val teamId: Int
)
{
    companion object {
        val Empty = Profile(-1, "", SimpleDate(0, 0, 0), Gender.NONE, "", "", "", "", 0)
    }
}
