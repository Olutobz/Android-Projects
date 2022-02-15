package com.olutoba.marsrealestate.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.olutoba.marsrealestate.R
import com.olutoba.marsrealestate.network.MarsProperty

class DetailViewModel(marsProperty: MarsProperty, application: Application) :
    AndroidViewModel(application) {

    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }

    val displayPropertyPrice = Transformations.map(selectedProperty) {
        application.applicationContext.getString(
            when (it.isRental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, it.price
        )
    }

    val displayPropertyType = Transformations.map(selectedProperty) {
        application.applicationContext.getString(
            R.string.display_type,
            application.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }
            )
        )
    }

}