package com.techun.memorygame.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferencesProvider {

    fun getFirstCardSelected(context: Context): Int {
        return prefs(context).getInt(SharedPrefHelper.FIRST_CARD_SELECTED.value, -1)
    }

    fun setFirstCardSeleted(context: Context, value: Int) {
        val editor = prefs(context).edit()
        editor.putInt(SharedPrefHelper.FIRST_CARD_SELECTED.value, value).apply()
    }

    fun remove(context: Context, key: SharedPrefHelper) {
        val editor = prefs(context).edit()
        editor.remove(key.value).apply()
    }

    fun deleteAllShared(context: Context) {
        val editor = prefs(context).edit()
        editor.clear().apply()
    }

    private fun prefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}