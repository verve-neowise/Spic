package com.neowise.spic.ui

import com.neowise.spic.R
import com.neowise.spic.model.StudentBehavior

enum class Behaviour(val state: StudentBehavior, val icon: Int, val color: Int, val text: Int) {
    POSITIVELY(StudentBehavior.POSITIVELY, R.drawable.ic_good, R.color.accent, R.string.good),
    BAD(StudentBehavior.BAD, R.drawable.ic_bad, R.color.accent_red, R.string.bad)
    ;

    companion object {

        fun fromState(state: StudentBehavior): Behaviour {
            return values().find { it.state == state } ?: throw NoSuchElementException(state.name)
        }
    }
}