package com.neowise.spic.ui.activities

import com.neowise.spic.model.Role
import com.neowise.spic.ui.activities.profiles.ClubActivity
import com.neowise.spic.ui.activities.profiles.ParentActivity
import com.neowise.spic.ui.activities.profiles.PlayerActivity
import com.neowise.spic.ui.activities.profiles.TrainerActivity

object ActivityRoute {

    private val map = hashMapOf(
        Role.PLAYER to PlayerActivity::class.java,
        Role.TRAINER to TrainerActivity::class.java,
        Role.PARENT to ParentActivity::class.java,
        Role.LEADER to ClubActivity::class.java,
        Role.NONE to UndefinedRoleActivity::class.java
    )

    operator fun get(role: Role) = map[role]
}