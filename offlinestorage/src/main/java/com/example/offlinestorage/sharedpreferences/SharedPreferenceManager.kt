package com.example.offlinestorage.sharedpreferences

import com.example.offlinestorage.model.User

/**
 * Created By: Onikoyi Damola Olutoba
 * DATE: 04 December, 2023
 * EMAIL: damexxey94@gmail.com
 */
interface SharedPreferenceManager {
    fun saveUser(user: User)
    fun getUser(): User
}