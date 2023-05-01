package com.neowise.spic.ui.activities.profiles.menu

import com.neowise.spic.R

enum class Menu(val icon: Int, val title: Int) {

    SCHEDULE(R.drawable.ic_schedule, R.string.schedule),
    TEAM(R.drawable.ic_team, R.string.team),
    STATISTICS(R.drawable.ic_statistics, R.string.statistics),
    EVENTS(R.drawable.ic_events, R.string.events),
    PLAYERS(R.drawable.ic_team, R.string.players),
    TEAMS(R.drawable.ic_team, R.string.teams),
    TRAINERS(R.drawable.ic_trainer, R.string.trainers),
    ATTENDANCE(R.drawable.ic_attendance, R.string.attendance),
    PROFILE(R.drawable.ic_user, R.string.profile),
    EDIT_TEAM(R.drawable.ic_edit_fill, R.string.edit_team),
    PARENTS(R.drawable.ic_parents, R.string.parents),
    ALL_USERS(R.drawable.ic_all_users, R.string.all_users),
    DELETE(R.drawable.ic_delete_fill, R.string.delete_team),
    CONTACTS(R.drawable.ic_contacts, R.string.contacts);
}