package com.nativkod.android.weather.di

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nativkod.android.weather.BuildConfig
import com.nativkod.android.weather.helpers.Constants
import com.nativkod.android.weather.network.OpenWeatherApiService
import com.nativkod.android.weather.network.OpenWeatherApiServiceHelper
import com.nativkod.android.weather.network.OpenWeatherApiServiceHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(application: Application, client: OkHttpClient): Retrofit
            = retrofitConfiguration(application, client)

    private fun retrofitConfiguration(
        application: Application,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .build()
    }
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG)
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        else
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.NONE
            }
    }

    @Provides
    @Singleton
    fun providesOpenWeatherApiService(retrofit: Retrofit): OpenWeatherApiService {
        return retrofit.create(OpenWeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApiServiceHelper(openWeatherApiServiceHelper: OpenWeatherApiServiceHelperImpl): OpenWeatherApiServiceHelper = openWeatherApiServiceHelper
}