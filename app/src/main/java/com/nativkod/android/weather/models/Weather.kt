package com.nativkod.android.weather.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(val id: Int?, val main: String?, val description: String?, val icon : String?): Parcelable{

    fun getImageUrl(): String{
        return  "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}