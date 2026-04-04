package com.weather.vibe.di

import com.weather.vibe.core.ai.di.CoreAiModule
import com.weather.vibe.core.network.di.CoreNetworkModule
import org.koin.core.annotation.Module

@Module(
  includes = [
    CoreAiModule::class,
    CoreNetworkModule::class,
    WeatherModule::class,
    SettingsModule::class,
    SearchModule::class
  ]
)
class AppModule
