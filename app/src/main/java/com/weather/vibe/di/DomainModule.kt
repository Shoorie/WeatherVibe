package com.weather.vibe.di

import com.weather.vibe.data.repository.GeocodingRepositoryImpl
import com.weather.vibe.data.repository.WeatherRepositoryImpl
import com.weather.vibe.domain.repository.GeocodingRepository
import com.weather.vibe.domain.repository.WeatherRepository
import com.weather.vibe.domain.usecase.GetWeatherUseCase
import com.weather.vibe.domain.usecase.SearchLocationUseCase
import org.koin.dsl.module

val domainModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    single<GeocodingRepository> { GeocodingRepositoryImpl(get()) }
    factory { GetWeatherUseCase(get()) }
    factory { SearchLocationUseCase(get()) }
}
