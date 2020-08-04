package com.nativkod.android.weather.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("SELECT * FROM currentWeather")
    fun getCurrentWeatherAll() : LiveData<List<CurrentWeatherEntity>>

    @Query("SELECT * FROM currentWeather WHERE currentLocation = 1")
    fun getCurrentWeather() : LiveData<CurrentWeatherEntity>

    @Query("DELETE FROM currentWeather")
    fun deleteAll()

    @Query("DELETE FROM currentWeather WHERE id = :id")
    fun deleteCurrentWeather(id: Int)

    @Query("SELECT * FROM currentWeather WHERE id = :id")
    fun getCurrentWeather(id: Int): LiveData<CurrentWeatherEntity>

    @Update
    fun updateCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)


}