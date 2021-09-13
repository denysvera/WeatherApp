package com.nativkod.android.weather.network

import com.nativkod.android.weather.models.CurrentWeather
import com.nativkod.android.weather.models.ForecastWeather
import kotlinx.coroutines.Deferred
import retrofit2.http.Query


interface OpenWeatherApiServiceHelper {

    fun getLocationCurrentWeather(lat: String, lon:String, units:String, appid: String): Deferred<CurrentWeather>

    fun getLocationForecastWeather(lat: String, lon:String,  units:String, appid: String): Deferred<ForecastWeather>
}