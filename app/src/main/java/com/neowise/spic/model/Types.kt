package com.neowise.spic.model

import com.neowise.spic.R

enum class AttendanceState {
    ATTENDED,
    LATE,
    EXCUSED,
    NONE
}

enum class StudentBehavior {
    POSITIVELY,
    BAD,
    NONE
}

enum class Gender {
    MALE,
    FEMALE,
    NONE
}

enum class EventType {
    ALL,
    EVENT,
    WORKOUT
}

enum class Role(val text: Int) {
    PLAYER(R.string.player),
    TRAINER(R.string.trainer),
    PARENT(R.string.parent),
    LEADER(R.string.leader),
    NONE(R.string.undefined)
}