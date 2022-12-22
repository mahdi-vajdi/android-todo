package com.mahdivajdi.simpletodo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class UserPreferences(private val context: Context) {

    val authToken: Flow<String?>
        get() = context.dataStore.data
            .map { preferences ->
                preferences[KEY_AUTH]
            }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = token

        }
    }

    companion object {
        private val KEY_AUTH = stringPreferencesKey(name = "token")
    }
}