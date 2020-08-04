package com.nativkod.android.weather.models


import android.os.Parcelable
import com.nativkod.android.weather.database.CurrentWeatherEntity
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentWeather(val coord: Coord,
                          val weather: List<Weather>,
                            val base: String,
                            val main: Main,
                            val wind: Wind,
                            val clouds: Clouds,
                            @Json(name="dt")
                            val timeOfData: Long,
                            val timeZone: Long?,
                            val id: Int,
                            val name: String,
                          var currentLocation: Boolean?): Parcelable{

  fun toCurrentWeatherEntity():CurrentWeatherEntity{
    return CurrentWeatherEntity(coord, weather, base, main, wind, clouds, timeOfData, timeZone, id, name,currentLocation)
  }
  fun weatherDesc():String?{
    return weather[0].description
  }
}
