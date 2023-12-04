package com.example.offlinestorage.model

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created By: Onikoyi Damola Olutoba
 * DATE: 04 December, 2023
 * EMAIL: damexxey94@gmail.com
 */
class UserManager(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = "user_prefs")

    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    val userAgeFlow: Flow<Int> = context.dataStore.data.map {
        val age = it[USER_AGE_KEY] ?: 0
        if (age < 18) {
            Toast.makeText(context, "This user is under 18", Toast.LENGTH_SHORT).show()
        }
        age
    }

    val userGenderFlow: Flow<Boolean> = context.dataStore.data.map {
        it[USER_GENDER_KEY] ?: false
    }

    suspend fun storeUser(age: Int, name: String, isMale: Boolean) {
        context.dataStore.edit { userPref ->
            userPref[USER_NAME_KEY] = name
            userPref[USER_AGE_KEY] = age
            userPref[USER_GENDER_KEY] = isMale
        }
    }

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_GENDER_KEY = booleanPreferencesKey("USER_GENDER")
    }

}