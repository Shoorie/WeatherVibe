package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class DailyVibeCardUiState(
  val airQualityChip: AirQualityChipUiState? = null,
  val pollenChip: PollenChipUiState? = null,
  val vibe: DailyVibeUiState
)
