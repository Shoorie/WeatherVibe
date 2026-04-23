package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class LocationCardUi(
  val favoriteId: Long,
  val feelsLikeC: Int?,
  val highC: Int?,
  val hourlyTemperatures: ImmutableList<Float>,
  val humidityPercent: Int?,
  val label: String?,
  val locationId: Long,
  val lowC: Int?,
  val name: String,
  val precipitationChancePercent: Int?,
  val region: String,
  val temperatureC: Int?,
  val weather: LocationWeatherUi?,
  val windKph: Int?
)
