package com.neowise.spic.model

data class Token(
    val token: String,
    val role: Role
) {
    companion object {
        val Empty = Token("", Role.NONE)
    }

    val isValid: Boolean
        get() = token.isNotEmpty()
}