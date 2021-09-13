package com.nativkod.android.weather.network
import com.nativkod.android.weather.models.CurrentWeather
import com.nativkod.android.weather.models.ForecastWeather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApiService {

    @GET("data/2.5/weather?")
    fun getLocationCurrentWeather(@Query("lat") lat: String, @Query("lon") lon:String, @Query("units") units:String, @Query("appid") appid: String) :Deferred<CurrentWeather>

    @GET("data/2.5/forecast?")
    fun getLocationForecastWeather(@Query("lat") lat: String, @Query("lon") lon:String, @Query("units") units:String, @Query("appid") appid: String): Deferred<ForecastWeather>
}
