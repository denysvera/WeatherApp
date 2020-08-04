package com.nativkod.android.weather.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nativkod.android.weather.adapters.GenericCollectionAdapterFactory
import com.nativkod.android.weather.models.CurrentWeather
import com.nativkod.android.weather.models.ForecastWeather
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


private val moshi = Moshi.Builder()
    .add(GenericCollectionAdapterFactory(ArrayList::class.java){ArrayList()})
    .add(KotlinJsonAdapterFactory())
    .build()

val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}
val client = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .writeTimeout(1, TimeUnit.MINUTES)
    .readTimeout(1, TimeUnit.MINUTES)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(nullOnEmptyConverterFactory)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl("https://api.openweathermap.org/")
    .client(client)
    .build()
interface OpenWeatherApiService {

    @GET("data/2.5/weather?")
    fun getLocationCurrentWeather(@Query("lat") lat: String, @Query("lon") lon:String, @Query("units") units:String, @Query("appid") appid: String) :Deferred<CurrentWeather>

    @GET("data/2.5/forecast?")
    fun getLocationForecastWeather(@Query("lat") lat: String, @Query("lon") lon:String, @Query("units") units:String, @Query("appid") appid: String): Deferred<ForecastWeather>
}

object OpenWeatherApi{
    val retrofitService: OpenWeatherApiService by lazy {
        retrofit.create(OpenWeatherApiService::class.java)
    }
    val endpoint = retrofit

}