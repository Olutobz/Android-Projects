package com.olutoba.sleepqualitytracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.olutoba.sleepqualitytracker.database.SleepDatabaseDao

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application) : AndroidViewModel(application) {

}

