package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.settings.usecase.SaveUserSettings
import com.weather.vibe.domain.weather.usecase.DetermineWeatherRefreshStrategy
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.weather.usecase.InvalidateWeatherSuggestion
import org.koin.core.annotation.Factory

@Factory
internal class HomeUseCases(
  val determineWeatherRefreshStrategy: DetermineWeatherRefreshStrategy,
  val generateWeatherSuggestion: GenerateWeatherSuggestion,
  val getCurrentWeatherKey: GetCurrentWeatherKey,
  val getWeather: GetWeather,
  val invalidateWeatherSuggestion: InvalidateWeatherSuggestion,
  val observeUserSettings: ObserveUserSettings,
  val saveUserSettings: SaveUserSettings
)
