package com.neowise.spic.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neowise.spic.model.*

fun <T> parse(json: String, type: Class<T>): T {
    return Gson().fromJson(json, type)
}

fun json(obj: Any) : String {
    return Gson().toJson(obj)
}

fun parseAttendanceList(json: String) : TeamAttendance {
    val type = object : TypeToken<List<Person>>() {}.type
    return Gson().fromJson(json, type)
}

fun parseUserList(json: String) : List<User>{
    val type = object : TypeToken<List<User>>() {}.type
    return Gson().fromJson(json, type)
}

fun parsePersonList(json: String) : List<Person>{
    val type = object : TypeToken<List<Person>>() {}.type
    return Gson().fromJson(json, type)
}

fun parseScheduleList(json: String) : List<Schedule>{
    val type = object : TypeToken<List<Schedule>>() {}.type
    return Gson().fromJson(json, type)
}


fun parseDetailList(json: String) : List<EventDetail>{
    val type = object : TypeToken<List<EventDetail>>() {}.type
    return Gson().fromJson(json, type)
}

fun parseTeamList(json: String) : List<Team>{
    val type = object : TypeToken<List<Team>>() {}.type
    return Gson().fromJson(json, type)
}