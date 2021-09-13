package com.nativkod.android.weather.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue

@Parcelize
data class Main(val temp: Double,
                @SerializedName("feels_like")
                val feelsLike: Double,
                @SerializedName("temp_min")
                val tempMin: Double,
                @SerializedName( "temp_max")
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
