package com.example.assgn1_app.userPreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class AppPreferences(
    val userName: String = ""
)

class AppStorage(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_storage")
        private object PreferenceKeys {
            val USERNAME = stringPreferencesKey("userName")
        }
    }

    val appPreferencesFlow: Flow<AppPreferences> =
        context.dataStore.data.map { prefs ->
            val userName = prefs[PreferenceKeys.USERNAME] ?: ""
            AppPreferences(userName)
        }

    suspend fun saveUserName(username: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.USERNAME] = username
        }
    }
}
