package com.example.circularplayground.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    val KEY_CURRENT_SHORT_TERM_UTIL = intPreferencesKey("currentShortTermUtilisation")
    val KEY_CHANGE_IN_SHORT_TERM = longPreferencesKey("changeInShortTerm")
    val KEY_CURRENT_LONG_TERM_UTIL = intPreferencesKey("currentLongTermUtilisation")
    val KEY_CHANGE_IN_LONG_TERM = longPreferencesKey("changeInLongTerm")
    val KEY_DAYS_UNTIL_NEXT_REPORT = intPreferencesKey("daysUntilNextReport")
}