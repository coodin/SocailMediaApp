package com.example.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.presentation.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferenceData(private val context: Context) {
    // to make sure there's only one instance
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")
    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    private val DARK_MODE_KEY = booleanPreferencesKey("light_dark_mode")
    private val NOTIFICATION_KEY = booleanPreferencesKey("notification")


    //get the saved email
    val getUserData: Flow<UserPreferences?> = context.dataStore.data
        .map { preferences ->
            val email = preferences[USER_EMAIL_KEY] ?: "FirstLast@gmail.com"
            val darkLightMode = preferences[DARK_MODE_KEY] ?: false
            val notification = preferences[NOTIFICATION_KEY] ?: false
            UserPreferences(
                email = email,
                darkLightMode = darkLightMode,
                notification = notification
            )
        }

    //change email into datastore
    suspend fun changeEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    // change template using datastore
    suspend fun changeDarkMode(isLight: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isLight
        }
    }

    // change notification status
    suspend fun changeNotification(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isEnabled
        }
    }
}