package com.weather.vibe.di

import com.weather.vibe.data.weather.di.DataWeatherModule
import com.weather.vibe.domain.weather.di.DomainWeatherModule
import com.weather.vibe.feature.home.di.FeatureHomeModule
import org.koin.core.annotation.Module

@Module(
  includes = [
    FeatureHomeModule::class,
    DomainWeatherModule::class,
    DataWeatherModule::class
  ]
)
class WeatherModule
