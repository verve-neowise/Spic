package com.neowise.spic.model

import com.neowise.spic.ui.adapters.Markable

data class Team(
    val id: Int,
    val code: String,
    val name: String,
    val activity: String,
    override var marked: Boolean = false
): Markable {

    override fun toString(): String {
        return name
    }

    companion object {
        val Empty = Team(0, "", "", "")
    }
}