package com.neowise.spic.model

import com.neowise.spic.ui.adapters.Markable

data class Person(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val photoUrl: String,
    val role: Role,
    val teamName: String = "",
    override var marked: Boolean = false
): Markable {
    companion object {
        val Empty = Person(0, "", "", "", "", Role.NONE)
    }
}