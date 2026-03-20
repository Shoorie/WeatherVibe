package com.weather.vibe.di

import com.weather.vibe.core.network.createHttpClient
import com.weather.vibe.data.remote.api.GeocodingApiService
import com.weather.vibe.data.remote.api.WeatherApiService
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient() }
    single { WeatherApiService(get()) }
    single { GeocodingApiService(get()) }
}
