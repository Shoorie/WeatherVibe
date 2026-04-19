package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.airquality.usecase.GetEnvironmentalReadings
import com.weather.vibe.domain.alerts.usecase.ResolveHomeAlert
import com.weather.vibe.domain.settings.usecase.ExcludeGenre
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.vibe.usecase.CalculateDailyVibe
import com.weather.vibe.domain.weather.usecase.DetermineWeatherRefreshStrategy
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.weather.usecase.InvalidateWeatherSuggestion
import org.koin.core.annotation.Factory

@Factory
internal data class HomeUseCases(
  val calculateDailyVibe: CalculateDailyVibe,
  val determineWeatherRefreshStrategy: DetermineWeatherRefreshStrategy,
  val excludeGenre: ExcludeGenre,
  val generateWeatherSuggestion: GenerateWeatherSuggestion,
  val getCurrentWeatherKey: GetCurrentWeatherKey,
  val getEnvironmentalReadings: GetEnvironmentalReadings,
  val getWeather: GetWeather,
  val invalidateWeatherSuggestion: InvalidateWeatherSuggestion,
  val observeUserSettings: ObserveUserSettings,
  val resolveHomeAlert: ResolveHomeAlert
)
