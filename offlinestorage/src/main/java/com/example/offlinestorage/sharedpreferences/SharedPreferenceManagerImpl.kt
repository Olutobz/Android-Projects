package com.example.offlinestorage.sharedpreferences

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.offlinestorage.model.User


/**
 * Created By: Onikoyi Damola Olutoba
 * DATE: 04 December, 2023
 * EMAIL: damexxey94@gmail.com
 */
class SharedPreferenceManagerImpl(context: Context) : SharedPreferenceManager {

    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    // save user information to sharedPreferences
    override fun saveUser(user: User) {
        sharedPref.edit {
            putString(PREF_FIRST_NAME, user.firstName)
            putString(PREF_LAST_NAME, user.lastName)
            putLong(PREF_BIRTH_DAY, user.birthday)
                .apply()
        }
    }


    // retrieve user information from sharedPreferences
    override fun getUser(): User {
        val firstName = sharedPref.getString(PREF_FIRST_NAME, "")
        val lastName = sharedPref.getString(PREF_LAST_NAME, "")
        val birthday = sharedPref.getLong(PREF_BIRTH_DAY, 0)
        return User(firstName, lastName, birthday)
    }

    companion object {
        const val PREF_FIRST_NAME = "PREF_FIRST_NAME"
        const val PREF_LAST_NAME = "PREF_LAST_NAME"
        const val PREF_BIRTH_DAY = "PREF_BIRTH_DAY"
    }
}