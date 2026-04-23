package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class LocationCardUiState(
  val favoriteId: Long,
  val feelsLike: String?,
  val high: String?,
  val hourlyTemperatures: ImmutableList<Float>,
  val humidityPercent: Int?,
  val label: String?,
  val locationId: Long,
  val low: String?,
  val name: String,
  val precipitationChancePercent: Int?,
  val region: String,
  val temperature: String?,
  val weather: LocationWeatherUi?,
  val windKph: Int?
)

