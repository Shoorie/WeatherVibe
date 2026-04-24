package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherMetrics
import com.weather.vibe.domain.weather.usecase.ObserveCachedWeather
import org.koin.core.annotation.Factory

@Factory
internal data class DetailsUseCases(
  val getCurrentWeatherMetrics: GetCurrentWeatherMetrics,
  val observeCachedWeather: ObserveCachedWeather,
  val observeUserSettings: ObserveUserSettings
)
