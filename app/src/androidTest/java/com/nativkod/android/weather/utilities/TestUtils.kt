package com.nativkod.android.weather.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nativkod.android.weather.database.CurrentWeatherEntity
import com.nativkod.android.weather.models.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class TestUtils {
    companion object {
        val coord = Coord(18.51, -33.81)

        val weather = listOf(Weather(741, "Fog", "fog", "50n"))

        val main = Main( 282.57,
        282.11,
         280.93,
         285.37,
         1024,
         100)

        val wind = Wind( 0.5,
         0.0)

        val clouds = Clouds(0.0)

        val city = City( 3360687,
         "Table View",
        coord,
        "ZA",
         7200,
         1596260281,
         1596297994)

        val monWeather = ForecastListItem(main, weather, clouds, wind,0,"2020-08-03 03:00:00")
        val tueWeather = ForecastListItem(main, weather, clouds, wind,0,"2020-08-04 03:00:00")

        val wedWeather = ForecastListItem(main, weather, clouds, wind,0,"2020-08-05 03:00:00")

        val thurWeather = ForecastListItem(main, weather, clouds, wind,0,"2020-08-06 03:00:00")

        val friWeather = ForecastListItem(main, weather, clouds, wind,0,"2020-08-07 03:00:00")

        val fiveDayForecastWeather = listOf(monWeather, tueWeather, wedWeather, thurWeather,
            friWeather)

    }

}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
