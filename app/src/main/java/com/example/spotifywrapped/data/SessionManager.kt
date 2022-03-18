package com.example.spotifywrapped.data

import android.content.Context
import android.content.SharedPreferences
import com.example.spotifywrapped.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val QUERY_TYPE = "query_type"
        const val QUERY_RANGE = "query_range"
    }

    fun setType(type: String) {
        val editor = prefs.edit()
        editor.putString(QUERY_TYPE, type)
        editor.apply()
    }

    fun getType(): String? {
        return prefs.getString(QUERY_TYPE, null)
    }

    fun setRange(range: String) {
        val editor = prefs.edit()
        editor.putString(QUERY_RANGE, range)
        editor.apply()
    }

    fun getRange(): String? {
        return prefs.getString(QUERY_RANGE, null)
    }

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

}