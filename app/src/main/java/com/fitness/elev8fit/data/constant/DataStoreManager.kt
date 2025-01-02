package com.fitness.elev8fit.data.constant



import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create DataStore instance
val Context.dataStore by preferencesDataStore(name = "auth_preferences")

object DataStoreManager {

    // Define keys for authentication state and onboarding state
    private val AUTH_STATE_KEY = booleanPreferencesKey("is_authenticated")
    private val ONBOARDING_STATE_KEY = booleanPreferencesKey("has_seen_onboarding")

    // Save the authentication state (true for logged in, false for logged out)
    suspend fun saveAuthState(context: Context, isAuthenticated: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_STATE_KEY] = isAuthenticated
        }
    }

    // Save the onboarding state (true for completed onboarding)


    // Retrieve the authentication state
    fun getAuthState(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[AUTH_STATE_KEY] ?: false // Default to false if not found
            }
    }

    // Retrieve the onboarding state


}
