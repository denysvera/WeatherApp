package com.nativkod.android.weather.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue

@Parcelize
data class Main(val temp: Double,
                @Json(name = "feels_like")
                val feelsLike: Double,
                @Json(name = "temp_min")
                val tempMin: Double,
                @Json(name = "temp_max")
                val tempMax: Double,
                val pressure: Int,
                val humidity: Int): Parcelable{

    fun getTempString(): String{
        return (temp.roundToInt()).toString() + "\u2103"
    }

    fun getMaxTempString(): String{
        return (tempMax.roundToInt()).toString() + "\u2103"
    }

    fun getMinTempString(): String{
        return (tempMin.roundToInt()).toString() + "\u2103"
    }
}
