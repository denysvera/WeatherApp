package com.nativkod.android.weather.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nativkod.android.weather.models.City

@Dao
interface ForecastWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastWeather(forecastWeatherEntity: ForecastWeatherEntity)


    @Query("SELECT * FROM forecastWeather")
    fun getForecastWeatherAll() : LiveData<List<ForecastWeatherEntity>>

    @Query("SELECT * FROM forecastWeather WHERE currentLocation = 1")
    fun getForecastWeather() : LiveData<ForecastWeatherEntity>

    @Query("SELECT * FROM forecastWeather WHERE city = :city")
    fun getForecastWeather(city: City) : LiveData<ForecastWeatherEntity>

    @Query("DELETE FROM forecastWeather")
    fun deleteAll()

    @Query("DELETE FROM forecastWeather WHERE city = :city")
    fun deleteForecastWeather(city: City)

    @Update
    fun updateForecastWeather(forecastWeatherEntity: ForecastWeatherEntity)
}