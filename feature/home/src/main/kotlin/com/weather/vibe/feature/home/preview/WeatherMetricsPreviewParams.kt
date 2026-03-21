package com.weather.vibe.feature.home.preview

import androidx.compose.runtime.Immutable

@Immutable
internal data class WeatherMetricsPreviewParams(
  val humidity: Int,
  val precipitationProbability: Int,
  val windDirection: Double,
  val windSpeed: Double
)
