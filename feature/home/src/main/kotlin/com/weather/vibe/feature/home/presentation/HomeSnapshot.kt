package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherKey

internal data class HomeSnapshot(
  val weatherData: WeatherData? = null,
  val weatherKey: WeatherKey? = null
)
