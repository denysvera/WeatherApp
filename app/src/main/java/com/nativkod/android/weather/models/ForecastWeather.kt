package com.nativkod.android.weather.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.nativkod.android.weather.database.ForecastWeatherEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastWeather(val city: City,
                            var list: List<ForecastListItem>,
                           var currentLocation: Boolean?): Parcelable{

    fun toForecastWeatherEntity():ForecastWeatherEntity{
        return ForecastWeatherEntity(city, list,currentLocation)
    }
}