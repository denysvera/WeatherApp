package com.nativkod.android.weather.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class CurrentWeatherDaoTest {

    private lateinit var currentWeatherDao: CurrentWeatherDao
    private lateinit var db : AppDatabase
    val currentWeatherEntity = CurrentWeatherEntity(TestUtils.coord,TestUtils.weather,"stations",TestUtils.main, TestUtils.wind,TestUtils.clouds, 1596245393L,7200L,3360687,"Table View",true)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        currentWeatherDao = db.currentWeatherDao()

    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertCurrentWeatherTest(){

        currentWeatherDao.insertCurrentWeather(currentWeatherEntity)
        val currentWeather = db.currentWeatherDao().getCurrentWeather(3360687)
        assert(currentWeather.getOrAwaitValue() == currentWeatherEntity)

    }

    @Test
    fun updateCurrentWeatherTest(){

        currentWeatherDao.insertCurrentWeather(currentWeatherEntity)
        val weather = listOf(Weather(770, "Clear", "all clear", "70n"))
        currentWeatherEntity.weather = weather
        db.currentWeatherDao().updateCurrentWeather(currentWeatherEntity)
        val currentWeather = db.currentWeatherDao().getCurrentWeather(3360687)
        assert(currentWeather.getOrAwaitValue().weather == weather)
    }

    @Test
    fun deleteCurrentWeatherTest(){

        currentWeatherDao.insertCurrentWeather(currentWeatherEntity)
        db.currentWeatherDao().deleteCurrentWeather(3360687)
        val currentWeather = db.currentWeatherDao().getCurrentWeather(3360687)
        assert(currentWeather.getOrAwaitValue() == null)

    }
}


