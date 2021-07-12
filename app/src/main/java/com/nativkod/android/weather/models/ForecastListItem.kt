package com.nativkod.android.weather.models

import android.os.Parcelable
import com.nativkod.android.weather.helpers.Utils
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class ForecastListItem(val main: Main,
                            val weather: List<Weather>,
                            val clouds: Clouds,
                            val wind: Wind,
                            val visibility: Int,
                            @Json(name = "dt_txt")
                            val dateTime: String): Parcelable{
    var isMoreDetailsShown = false
    var dayForecastList = listOf<DayForecastItem>()
    fun getDay():String{
        return Utils.formatDate(dateTime,"yyyy-MM-dd HH:mm:ss","EEEE")
    }
    fun getTime():String{
        return Utils.formatDate(dateTime,"yyyy-MM-dd HH:mm:ss","HH:mm")
    }

    fun getWindSpeed():String{
        val speed = wind.speed
        return "$speed m/s"
    }

    fun getHumidity(): String{
        val humidity = main.humidity
        return "$humidity%"
    }
    fun getCloudsString():String{
        val clouds = clouds.all
        return "$clouds%"
    }
    fun getDayForecastItem(): DayForecastItem{
        return DayForecastItem(main.getMaxTempString(),weather[0].icon!!,getTime())
    }
    fun getImageUrl():String{
        val icon = weather[0].icon
        return "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}
