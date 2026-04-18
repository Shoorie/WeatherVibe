package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

internal data class HomeSnapshot(
  val readings: EnvironmentalReadings = EnvironmentalReadings.Empty,
  val weatherData: WeatherData? = null,
  val weatherKey: WeatherKey? = null,
  val weatherSuggestion: WeatherSuggestion? = null
)
