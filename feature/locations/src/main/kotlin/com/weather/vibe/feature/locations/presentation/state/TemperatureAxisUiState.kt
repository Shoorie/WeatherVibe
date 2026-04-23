package com.weather.vibe.feature.locations.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class TemperatureAxisUiState(
  val min: String,
  val mid: String,
  val max: String
)
