package com.nativkod.android.weather.di

import android.content.Context
import androidx.room.Room
import com.nativkod.android.weather.database.AppDatabase
import com.nativkod.android.weather.database.CurrentWeatherDao
import com.nativkod.android.weather.database.ForecastWeatherDao
import com.nativkod.android.weather.network.OpenWeatherApiServiceHelper
import com.nativkod.android.weather.repository.WeatherAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providesCurrentWeatherDao(appDatabase: AppDatabase): CurrentWeatherDao = appDatabase.currentWeatherDao()

    @Provides
    fun providesForecastWeatherDao(appDatabase: AppDatabase): ForecastWeatherDao = appDatabase.forecastWeatherDao()

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(appContext.applicationContext,AppDatabase::class.java,"weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesWeatherAppRepository(appDatabase: AppDatabase,openWeatherApiServiceHelper: OpenWeatherApiServiceHelper): WeatherAppRepository = WeatherAppRepository(appDatabase,openWeatherApiServiceHelper)
}