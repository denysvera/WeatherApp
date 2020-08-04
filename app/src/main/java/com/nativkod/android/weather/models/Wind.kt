package com.nativkod.android.weather.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(val speed: Double, val deg: Double): Parcelable