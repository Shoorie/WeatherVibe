package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

@Immutable
data class LocationCardUiState(
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
) {

  @Stable
  fun isSelected(ids: ImmutableSet<Long>): Boolean =
    favoriteId in ids
}
