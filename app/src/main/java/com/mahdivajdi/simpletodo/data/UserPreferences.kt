package com.mahdivajdi.simpletodo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class UserPreferences(private val context: Context) {

    val refreshToken: Flow<String?>
        get() = context.dataStore.data
            .map { preferences ->
                preferences[REFRESH_TOKEN]
            }

    val accessToken: Flow<String?>
        get() = context.dataStore.data
            .map { preferences ->
                preferences[ACCESS_TOKEN]
            }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    companion object {
        private val REFRESH_TOKEN = stringPreferencesKey(name = "refresh_token")
        private val ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
    }
}