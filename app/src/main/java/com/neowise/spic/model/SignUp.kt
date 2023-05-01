package com.neowise.spic.model

data class SignUp(
    val username: String,
    val password: String,
    val name: String,
    val birthDay: String
) {

    companion object {
        val Empty = SignUp("", "", "", "")
    }
}
