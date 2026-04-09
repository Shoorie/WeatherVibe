package com.weather.vibe.di

import com.weather.vibe.core.ai.di.CoreAiModule
import com.weather.vibe.core.network.di.CoreNetworkModule
import com.weather.vibe.core.time.di.CoreTimeModule
import org.koin.core.annotation.Module

@Module(
  includes = [
    CoreAiModule::class,
    CoreNetworkModule::class,
    CoreTimeModule::class,
    WeatherModule::class,
    SettingsModule::class,
    SearchModule::class
  ]
)
class AppModule
