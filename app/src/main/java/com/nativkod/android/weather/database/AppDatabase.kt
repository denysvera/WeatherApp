package com.nativkod.android.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nativkod.android.weather.helpers.DataConverter

@Database(entities = [CurrentWeatherEntity::class,ForecastWeatherEntity::class], version = 8)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun forecastWeatherDao(): ForecastWeatherDao

}