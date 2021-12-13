package com.olutoba.sleepqualitytracker.sleepdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olutoba.sleepqualitytracker.database.SleepDatabaseDao


class SleepDetailViewModelFactory(
    private val sleepNightKey: Long = 0L,
    private val datasource: SleepDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepNightKey, datasource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}