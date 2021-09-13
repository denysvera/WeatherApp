package com.nativkod.android.weather.network

import com.nativkod.android.weather.models.CurrentWeather
import com.nativkod.android.weather.models.ForecastWeather
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class OpenWeatherApiServiceHelperImpl @Inject constructor(private val openWeatherApiService: OpenWeatherApiService): OpenWeatherApiServiceHelper{
    override fun getLocationCurrentWeather(
        lat: String,
        lon: String,
        units: String,
        appid: String
    ): Deferred<CurrentWeather> = openWeatherApiService.getLocationCurrentWeather(lat, lon, units, appid)

    override fun getLocationForecastWeather(
        lat: String,
        lon: String,
        units: String,
        appid: String
    ): Deferred<ForecastWeather> = openWeatherApiService.getLocationForecastWeather(lat, lon, units, appid)
}