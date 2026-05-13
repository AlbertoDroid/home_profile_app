package com.albersa.homeprofile.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppPreferences(private val context: Context) {

    companion object {
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val LAST_CALENDAR_GENERATED_AT = longPreferencesKey("last_calendar_generated_at")
        val NOTIFICATION_PREFERENCE = stringPreferencesKey("notification_preference")
    }

    val onboardingComplete: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[ONBOARDING_COMPLETE] ?: false }

    val lastCalendarGeneratedAt: Flow<Long> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[LAST_CALENDAR_GENERATED_AT] ?: 0L }

    val notificationPreference: Flow<String> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[NOTIFICATION_PREFERENCE] ?: "weekly_digest" }

    suspend fun setOnboardingComplete(complete: Boolean) {
        context.dataStore.edit { it[ONBOARDING_COMPLETE] = complete }
    }

    suspend fun setLastCalendarGeneratedAt(timestamp: Long) {
        context.dataStore.edit { it[LAST_CALENDAR_GENERATED_AT] = timestamp }
    }

    suspend fun setNotificationPreference(preference: String) {
        context.dataStore.edit { it[NOTIFICATION_PREFERENCE] = preference }
    }
}
