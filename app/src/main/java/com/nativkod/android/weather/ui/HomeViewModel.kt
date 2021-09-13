package com.nativkod.android.weather.ui

import android.app.Application
import androidx.lifecycle.*
import com.nativkod.android.weather.database.CurrentWeatherEntity
import com.nativkod.android.weather.helpers.LocationLiveData
import com.nativkod.android.weather.repository.WeatherAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WeatherUpdateStatus {LOADING, ERROR, DONE}
@HiltViewModel
class HomeViewModel  @Inject constructor (private val weatherAppRepository: WeatherAppRepository, application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<WeatherUpdateStatus>()
    val status: LiveData<WeatherUpdateStatus>
        get() = _status
    private val _currentWeather = MutableLiveData<CurrentWeatherEntity>()
    val currentWeather = weatherAppRepository.currentWeather
    val currentForecast = weatherAppRepository.forecastWeather

    private val locationData = LocationLiveData(application)
    fun getLocationData() = locationData


    fun refreshCurrentLocationWeather(lat: String, lon: String){
        viewModelScope.launch {
            _status.value = WeatherUpdateStatus.LOADING
            weatherAppRepository.getCurrentLocWeather(lat, lon)
            _status.value = WeatherUpdateStatus.DONE
        }
    }

    fun refreshCurrentLocationForecast(lat: String, lon: String){
        viewModelScope.launch {
            weatherAppRepository.getCurrentLocForecast(lat, lon)
        }
    }
    fun getCurrentWeather(townId: Int){
        viewModelScope.launch {
            _currentWeather.value = weatherAppRepository.getCurrentWeather(townId).value
        }
    }

    fun refreshData(){
        locationData.getCurrentLocation()
    }

    fun stopFreshLocation(){
        locationData.stopLocationUpdates()
    }

}