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

    fun getDay():String{
        return Utils.formatDate(dateTime,"yyyy-MM-dd HH:mm:ss","EEEE")
    }
}
