package com.example.eShuttle.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(UserPreferences.APP_PREFERENCES)

class UserPreferences(context: Context) {


    private val dataStore: DataStore<Preferences> = context._dataStore

    companion object {
        const val APP_PREFERENCES = "app_preferences"
        val JWT_AUTHTOKEN = stringPreferencesKey("jwt_auth_token")
        val PHONENUMBER = stringPreferencesKey("phoneNumber")
    }

    // store authToken
    suspend fun saveAuthToken(jwt_authToken: String) {
        dataStore.edit { preferences ->
            preferences[JWT_AUTHTOKEN] = jwt_authToken
        }
    }

    // get  the key back using Flow
    val jwt_authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[JWT_AUTHTOKEN]
        }

    // store phoneNumber
    suspend fun savePhoneNumber(phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[PHONENUMBER] = phoneNumber
        }
    }

    // get  the key back using Flow
    val phoneNumber: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PHONENUMBER]
        }


    //clear Token
    suspend fun  clearToken(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}