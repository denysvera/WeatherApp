package com.nativkod.android.weather.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nativkod.android.weather.models.Weather
import com.nativkod.android.weather.utilities.TestUtils
import com.nativkod.android.weather.utilities.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ForecastWeatherDaoTest {

    private lateinit var forecastWeatherDao: ForecastWeatherDao
    private lateinit var db: AppDatabase
   private val forecastWeatherEntity = ForecastWeatherEntity(TestUtils.city,TestUtils.fiveDayForecastWeather,true)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        forecastWeatherDao = db.forecastWeatherDao()

    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertForecastWeatherTest(){

        forecastWeatherDao.insertForecastWeather(forecastWeatherEntity)
        val forecastWeather = db.forecastWeatherDao().getForecastWeather(TestUtils.city)
        assert(forecastWeather.getOrAwaitValue() == forecastWeatherEntity)

    }

    @Test
    fun updateForecastWeatherTest(){

        forecastWeatherDao.insertForecastWeather(forecastWeatherEntity)
        val weather = Weather(770, "Clear", "all clear", "70n")
        forecastWeatherEntity.list = TestUtils.fiveDayForecastWeather.asReversed()
        db.forecastWeatherDao().updateForecastWeather(forecastWeatherEntity)
        val forecastWeather = db.forecastWeatherDao().getForecastWeather(TestUtils.city).getOrAwaitValue()
        assert(forecastWeather.list[0] == TestUtils.fiveDayForecastWeather[4])
    }

    @Test
    fun deleteForecastWeatherTest(){

        forecastWeatherDao.insertForecastWeather(forecastWeatherEntity)
        db.forecastWeatherDao().deleteForecastWeather(TestUtils.city)
        val forecastWeather = db.forecastWeatherDao().getForecastWeather(TestUtils.city)
        assert(forecastWeather.getOrAwaitValue() == null)

    }

}