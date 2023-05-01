package com.neowise.spic

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.neowise.spic.model.Role
import com.neowise.spic.model.Token

class Preferences private constructor(private val context: Context) {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: Preferences? = null

        fun instance(context: Context): Preferences {
            if (instance == null) {
                instance = Preferences(context)
            }
            return instance!!
        }
    }

    var token: Token
    get() = loadToken()
    set(value) = saveToken(value)

    private fun loadToken() : Token {
        val prefs = preferences()

        val token = prefs.getString("token", "")!!
        val role = prefs.getString("role", Role.NONE.name)!!

        return Token(token, Role.valueOf(role))
    }

    private fun saveToken(token: Token) {
        preferences().edit().apply {
            putString("token", token.token)
            putString("role", token.role.name)
        }.apply()
    }


    private fun preferences() : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}