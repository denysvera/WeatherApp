package com.nativkod.android.weather.database

import android.graphics.Color
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nativkod.android.weather.helpers.DataConverter
import com.nativkod.android.weather.helpers.Utils
import com.nativkod.android.weather.models.*
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "currentWeather")
data class CurrentWeatherEntity( @TypeConverters(DataConverter::class)
                                 var coord: Coord,
                                 @TypeConverters(DataConverter::class)
                                var weather: List<Weather>,
                                val base: String,
                                 @TypeConverters(DataConverter::class)
                                var main: Main,
                                 @TypeConverters(DataConverter::class)
                                var wind: Wind,
                                 @TypeConverters(DataConverter::class)
                                var clouds: Clouds,
                                var timeOfData: Long,
                                val timeZone: Long?,
                                 @PrimaryKey
                                val id: Int,
                                val name: String,
                                 @ColumnInfo(name = "currentLocation")var currentLocation: Boolean?): Parcelable{
    fun toDomainCurrentWeather():CurrentWeather{
        return CurrentWeather(coord, weather, base, main, wind, clouds, timeOfData, timeZone, id, name,currentLocation)
    }
    fun weatherDesc():String?{
        return weather[0].description
    }
    var lastUpdated =  "Last updated: "+ Utils.getTimeAgo(timeOfData)

}

fun List<CurrentWeatherEntity>.asCurrentWeatherModel(): List<CurrentWeather>{
    return map{ CurrentWeather(coord = it.coord, weather = it.weather, base = it.base, main = it.main, wind = it.wind, clouds = it.clouds, timeOfData = it.timeOfData, timeZone = it.timeZone, id = it.id, name = it.name,currentLocation = it.currentLocation)}
}



@Entity(tableName = "forecastWeather")
data class ForecastWeatherEntity(
                                @PrimaryKey
            @TypeConverters(DataConverter::class)
                                 val city: City,
                                 @TypeConverters(DataConverter::class)
                                 var list: List<ForecastListItem>,
                                @ColumnInfo(name = "currentLocation")var currentLocation: Boolean?)