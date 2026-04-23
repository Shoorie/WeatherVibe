package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class LocationCompareUiState(
  val card: LocationCardUiState,
  val feelsLike: String,
  val high: String,
  val hourlyTemperatures: ImmutableList<Float>,
  val humidityPercent: Int,
  val low: String,
  val precipitationChancePercent: Int,
  val temperature: String,
  val weather: LocationWeatherUi,
  val windKph: Int
)
