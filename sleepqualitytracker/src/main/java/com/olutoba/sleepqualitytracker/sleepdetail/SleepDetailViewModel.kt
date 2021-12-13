package com.olutoba.sleepqualitytracker.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.olutoba.sleepqualitytracker.database.SleepDatabaseDao
import com.olutoba.sleepqualitytracker.database.SleepNight
import kotlinx.coroutines.Job


class SleepDetailViewModel(
    private val sleepNightKey: Long = 0L,
    datasource: SleepDatabaseDao
) : ViewModel() {

    val database = datasource

    private val viewModelJob = Job()

    private val night = MediatorLiveData<SleepNight>()

    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}