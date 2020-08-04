package com.nativkod.android.weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.nativkod.android.weather.database.AppDatabase
import com.nativkod.android.weather.database.CurrentWeatherEntity
import com.nativkod.android.weather.database.asCurrentWeatherModel
import com.nativkod.android.weather.helpers.API_KEY
import com.nativkod.android.weather.helpers.UNIT
import com.nativkod.android.weather.models.CurrentWeather

import com.nativkod.android.weather.network.OpenWeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherAppRepository (val database: AppDatabase, val service: OpenWeatherApiService){

     val currentWeather = database.currentWeatherDao().getCurrentWeather()


    val forecastWeather = database.forecastWeatherDao().getForecastWeather()

    suspend fun getCurrentLocWeather(lat: String, lon: String){

        withContext(Dispatchers.IO){
            val currentWeatherDef = service.getLocationCurrentWeather(lat,lon, UNIT,API_KEY)

            try {
                val currentW = currentWeatherDef.await()
                if(currentWeather ==null ){
                    currentW.currentLocation = true
                    database.currentWeatherDao().insertCurrentWeather(currentW.toCurrentWeatherEntity())
                }else{
                    if(currentWeather.value !=null) {
                        var currentLoc = currentWeather.value!!.id
                        if (currentLoc == currentW.id) {
                            currentW.currentLocation = true
                            database.currentWeatherDao()
                                .insertCurrentWeather(currentW.toCurrentWeatherEntity())
                        } else {
                            database.currentWeatherDao().deleteCurrentWeather(currentLoc)
                            currentW.currentLocation = true
                            database.currentWeatherDao()
                                .insertCurrentWeather(currentW.toCurrentWeatherEntity())
                        }
                    }else{
                        currentW.currentLocation = true
                        database.currentWeatherDao().insertCurrentWeather(currentW.toCurrentWeatherEntity())
                    }
                }


            }catch (ex: Exception){
                Log.d("GET_WEATHER_ERROR",ex.message)
            }
        }
    }



    fun getCurrentWeather(townId: Int) : LiveData<CurrentWeatherEntity> {

        return database.currentWeatherDao().getCurrentWeather(townId)
    }

    suspend fun getCurrentLocForecast(lat: String, lon: String){
        withContext(Dispatchers.IO){
            val forecastWeatherDef = service.getLocationForecastWeather(lat,lon,UNIT,API_KEY)
            try {

                val forecastW = forecastWeatherDef.await()
                if(forecastWeather.value == null ){
                    forecastW.currentLocation = true
                    database.forecastWeatherDao().insertForecastWeather(forecastW.toForecastWeatherEntity())
                }else{
                    var currentLoc = forecastWeather.value!!.city.id
                    if (currentLoc == forecastW.city.id){
                        forecastW.currentLocation = true
                        database.forecastWeatherDao().insertForecastWeather(forecastW.toForecastWeatherEntity())
                    }else{
                        database.forecastWeatherDao().deleteForecastWeather(forecastWeather.value!!.city)
                        forecastW.currentLocation = true
                        database.forecastWeatherDao().insertForecastWeather(forecastW.toForecastWeatherEntity())
                    }
                }

            }catch (ex: Exception){
                Log.d("WEATHER_FORECAST_ERROR",ex.message)
            }
        }
    }


   // val currentWeatherEntity = database.currentWeatherDao().

}