package com.olutoba.sleepqualitytracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.olutoba.sleepqualitytracker.database.SleepDatabaseDao
import com.olutoba.sleepqualitytracker.database.SleepNight
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    private val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    // Note: The coroutine needs  a Job, Scope and dispatcher

    private var viewModelJob = Job()
    private var uiCoroutineScope =
        CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = database.getAllNights()

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiCoroutineScope.launch {
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
        uiCoroutineScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()

        }
    }

    fun onStopTracking() {
        uiCoroutineScope.launch {
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

    // Cancel all coroutines here
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClear() {
        uiCoroutineScope.launch {
            clear()
            tonight.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    /*  This is the coroutine usage pattern
      fun someWorkNeedsToBeDone() {
          uiCoroutineScope.launch {
             suspendFunction() // so that we don't block UI thread while waiting for results
          }
       }

        suspend fun longRunningWork() {

             withContext(Dispatcher.IO) {
                 doLongRunningWork()
             }
        }
    */

}
