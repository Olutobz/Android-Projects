package com.olutoba.marsrealestate.ui.fragments.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olutoba.marsrealestate.network.MarsApi
import com.olutoba.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

enum class MarsApiStatus {
    LOADING,
    ERROR,
    DONE
}

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [LiveData]. The Retrofit service returns a List of MarsProperty, which
     * is the result of the transaction.
     */
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                _status.value = MarsApiStatus.LOADING
                // this runs on a thread managed by retrofit
                val listResult = MarsApi.retrofitApiService.getProperties()
                _status.value = MarsApiStatus.DONE
                if (listResult.isNotEmpty()) {
                    _properties.value = listResult
                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
}