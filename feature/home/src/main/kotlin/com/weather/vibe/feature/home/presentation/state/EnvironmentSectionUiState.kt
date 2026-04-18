package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class EnvironmentSectionUiState(
  val airQualityChip: AirQualityChipUiState? = null,
  val alert: HomeAlertUiState? = null,
  val pollenChip: PollenChipUiState? = null
)
