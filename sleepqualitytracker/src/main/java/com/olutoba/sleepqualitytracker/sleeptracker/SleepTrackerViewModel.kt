package com.olutoba.sleepqualitytracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.olutoba.sleepqualitytracker.database.SleepDatabaseDao
import com.olutoba.sleepqualitytracker.database.SleepNight
import com.olutoba.sleepqualitytracker.utils.formatNights
import kotlinx.coroutines.*


class SleepTrackerViewModel(
    private val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()
    val nights = database.getAllNights()

    /**
     * Converted nights to Spanned for displaying.
     */
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    /**
     * If tonight has not been set, then the START button should be visible.
     */
    val startButtonVisible = Transformations.map(tonight) {
        it == null
    }

    /**
     * If tonight has been set, then the STOP button should be visible.
     */
    val stopButtonVisible = Transformations.map(tonight) {
        it != null
    }

    /**
     * If there are any nights in the database, show the CLEAR button.
     */
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality: LiveData<SleepNight?>
        get() = _navigateToSleepQuality

    private val _navigateToSleepDataQuality = MutableLiveData<Long?>()
    val navigateToSleepDataQuality: LiveData<Long?>
        get() = _navigateToSleepDataQuality

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDataQuality.value = id
    }


    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

    /**
     * Call this immediately after navigating to SleepQualityFragment.
     * It will clear the navigation request, so if the user rotates their phone
     * it won't navigate twice.
     */
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
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

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    /** Executes when the START button is clicked */
    fun onStartTracking() {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    /** Executes when the STOP button is clicked */
    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            // Set state to navigate to the SleepQualityFragment.
            _navigateToSleepQuality.value = oldNight
        }
    }

    /** Executes when the CLEAR button is clicked */
    fun onClear() {
        uiScope.launch {
            // Clear the database table.
            clear()
            // And clear tonight since it's no longer in the database
            tonight.value = null
        }
        // Show a snackbar message, because it's friendly.
        _showSnackBarEvent.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}