package com.neowise.spic.model

data class Account(val username: String, val password: String) {
    companion object {
        val Empty = Account("", "")
    }
}