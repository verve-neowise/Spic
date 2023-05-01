package com.neowise.spic.model

data class Contacts(
    val email: String,
    val phone: String,
    val description: String
) {
    companion object {
        val Empty = Contacts("", "", "")
    }
}