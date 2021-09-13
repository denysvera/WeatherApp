package com.nativkod.android.weather.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nativkod.android.weather.helpers.Utils
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastListItem(val main: Main,
                            val weather: List<Weather>,
                            val clouds: Clouds,
                            val wind: Wind,
                            val visibility: Int,
                            val dt_txt: String?): Parcelable{
    var isMoreDetailsShown = false
    var dayForecastList = listOf<DayForecastItem>()
    fun getDay():String{
        return if (dt_txt != null){
            Utils.formatDate(dt_txt,"yyyy-MM-dd HH:mm:ss","EEEE")
        }else{
            ""
        }

    }
    fun getTime():String{

        return if(dt_txt != null) {
            Utils.formatDate(dt_txt,"yyyy-MM-dd HH:mm:ss","HH:mm")
        }else{
            ""
        }
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
