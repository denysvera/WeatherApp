package com.nativkod.android.weather.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nativkod.android.weather.repository.WeatherAppRepository
import java.lang.IllegalArgumentException

class ViewModelFactory (private val weatherAppRepository: WeatherAppRepository, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(weatherAppRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}