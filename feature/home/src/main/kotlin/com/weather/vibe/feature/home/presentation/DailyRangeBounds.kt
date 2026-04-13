package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.DailyWeather

internal data class DailyRangeBounds(
  val day: DailyWeather,
  val min: Int,
  val max: Int
)
