package com.nativkod.android.weather.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(val id: Int,
                val name: String,
                val coord: Coord,
                val country: String,
                val timeZone: Long?,
                val sunrise: Long?,
                val sunset: Long?): Parcelable