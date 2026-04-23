package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class LocationCompareUi(
  val card: LocationCardUi,
  val feelsLikeC: Int,
  val highC: Int,
  val hourlyTemperatures: ImmutableList<Float>,
  val humidityPercent: Int,
  val lowC: Int,
  val precipitationChancePercent: Int,
  val temperatureC: Int,
  val weather: LocationWeatherUi,
  val windKph: Int
)
