package com.olutoba.sleepqualitytracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.olutoba.sleepqualitytracker.database.SleepDatabaseDao
import com.olutoba.sleepqualitytracker.database.SleepNight
import com.olutoba.sleepqualitytracker.formatNights
import kotlinx.coroutines.*


class SleepTrackerViewModel(
    private val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    // Note: The coroutine needs  a Job, Scope and dispatcher

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = database.getAllNights()

    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var sleepNight = database.getTonight()
            if (sleepNight?.endTimeMilli != sleepNight?.startTimeMilli) {
                sleepNight = null
            }
            sleepNight
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    // Cancel all coroutines here
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
