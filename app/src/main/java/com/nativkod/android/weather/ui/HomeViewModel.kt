package com.nativkod.android.weather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nativkod.android.weather.database.CurrentWeatherEntity
import com.nativkod.android.weather.helpers.LocationLiveData
import com.nativkod.android.weather.repository.WeatherAppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class WeatherUpdateStatus {LOADING, ERROR, DONE}
class HomeViewModel(private val weatherAppRepository: WeatherAppRepository, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _status = MutableLiveData<WeatherUpdateStatus>()
    val status: LiveData<WeatherUpdateStatus>
        get() = _status
    private val _currentWeather = MutableLiveData<CurrentWeatherEntity>()
    val currentWeather = weatherAppRepository.currentWeather
    val currentForecast = weatherAppRepository.forecastWeather

    private val locationData = LocationLiveData(application)
    fun getLocationData() = locationData
    fun refreshCurrentLocationWeather(lat: String, lon: String){
        coroutineScope.launch {
            _status.value = WeatherUpdateStatus.LOADING
            weatherAppRepository.getCurrentLocWeather(lat, lon)
            _status.value = WeatherUpdateStatus.DONE
        }
    }

    fun refreshCurrentLocationForecast(lat: String, lon: String){
        coroutineScope.launch {
            weatherAppRepository.getCurrentLocForecast(lat, lon)
        }
    }
    fun getCurrentWeather(townId: Int){
        coroutineScope.launch {
            _currentWeather.value = weatherAppRepository.getCurrentWeather(townId).value
        }
    }

    fun refreshData(){
        refreshCurrentLocationWeather(locationData.value!!.latitude.toString(),locationData.value!!.longitude.toString())
        refreshCurrentLocationForecast(locationData.value!!.latitude.toString(),locationData.value!!.longitude.toString())
    }
}