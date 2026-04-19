package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

internal data class HomeSnapshot(
  val dailyVibe: DailyVibe? = null,
  val readings: EnvironmentalReadings = EnvironmentalReadings.Empty,
  val rejectedGenres: Set<String> = emptySet(),
  val weatherData: WeatherData? = null,
  val weatherKey: WeatherKey? = null,
  val weatherSuggestion: WeatherSuggestion? = null
)
