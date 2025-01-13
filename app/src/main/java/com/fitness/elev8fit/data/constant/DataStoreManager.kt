package com.fitness.elev8fit.data.constant



import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create DataStore instance
val Context.dataStore by preferencesDataStore(name = "auth_preferences")

object DataStoreManager {

    // Define keys for authentication state and onboarding state
    private val AUTH_STATE_KEY = booleanPreferencesKey("is_authenticated")
    private val ONBOARDING_STATE_KEY = booleanPreferencesKey("has_seen_onboarding")
    private val LANGUAGE_PREF_KEY = stringPreferencesKey("selected_language")
    // Save the authentication state (true for logged in, false for logged out)
    suspend fun saveAuthState(context: Context, isAuthenticated: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_STATE_KEY] = isAuthenticated
        }
    }
    // Retrieve the authentication state
    fun getAuthState(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[AUTH_STATE_KEY] ?: false // Default to false if not found
            }
    }

    // Retrieve the onboarding state

    suspend fun saveLanguagePreference(context: Context, languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_PREF_KEY] = languageCode
        }
    }

    fun getLanguagePreference(context: Context): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[LANGUAGE_PREF_KEY] ?: "en" // Default to "en" (English) if not found
            }
    }

}
