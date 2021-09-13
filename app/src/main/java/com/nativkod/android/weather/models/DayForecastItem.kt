package com.nativkod.android.weather.models

data class DayForecastItem(val maxTemp: String, val weatherIcon: String , val time: String?){
    fun getImageUrl(): String{
        return  "https://openweathermap.org/img/wn/$weatherIcon@2x.png"
    }
}