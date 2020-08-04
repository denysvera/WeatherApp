package com.nativkod.android.weather.helpers

import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nativkod.android.weather.models.*
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataConverter : Parcelable{

    @TypeConverter
    fun fromCurrentWeatherList(value: List<CurrentWeather>): String?{
        val gson = Gson()
        val type = object : TypeToken<List<CurrentWeather>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCurrentWeatherList(value: String?): List<CurrentWeather>?{
        val gson = Gson()
        val type = object : TypeToken<List<CurrentWeather>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromForecastListItemList(value: List<ForecastListItem>): String?{
        val gson = Gson()
        val type = object : TypeToken<List<ForecastListItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toForecastListItemList(value: String?): List<ForecastListItem>?{
        val gson = Gson()
        val type = object : TypeToken<List<ForecastListItem>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCoord(value: Coord?): String?{
        val gson = Gson()
        val type = object : TypeToken<Coord>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toCoord(value: String?): Coord?{
        val gson = Gson()
        val type = object : TypeToken<Coord>(){}.type
        return gson.fromJson(value,type)
    }

    @TypeConverter
    fun fromWeather(value: Weather?): String?{
        val gson = Gson()
        val type = object : TypeToken<Weather>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toWeather(value: String?): Weather?{
        val gson = Gson()
        val type = object : TypeToken<Weather>(){}.type
        return gson.fromJson(value,type)
    }

    @TypeConverter
    fun fromWeatherList(value: List<Weather>?): String?{
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toWeatherList(value: String?): List<Weather>?{
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>(){}.type
        return gson.fromJson(value,type)
    }

    @TypeConverter
    fun fromMain(value: Main?): String?{
        val gson = Gson()
        val type = object : TypeToken<Main>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toMain(value: String?): Main?{
        val gson = Gson()
        val type = object : TypeToken<Main>(){}.type
        return gson.fromJson(value,type)
    }

    @TypeConverter
    fun fromWind(value: Wind?): String?{
        val gson = Gson()
        val type = object : TypeToken<Wind>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toWind(value: String?): Wind?{
        val gson = Gson()
        val type = object : TypeToken<Wind>(){}.type
        return gson.fromJson(value,type)
    }

    @TypeConverter
    fun fromClouds(value: Clouds?): String?{
        val gson = Gson()
        val type = object : TypeToken<Clouds>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toClouds(value: String?): Clouds?{
        val gson = Gson()
        val type = object : TypeToken<Clouds>(){}.type
        return gson.fromJson(value,type)
    }

    @TypeConverter
    fun fromCity(value: City?): String?{
        val gson = Gson()
        val type = object : TypeToken<City>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun toCity(value: String?): City?{
        val gson = Gson()
        val type = object : TypeToken<City>(){}.type
        return gson.fromJson(value,type)
    }

}