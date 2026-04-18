package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class AirQualityPresentation(
  val airQualityChip: AirQualityChipUiState?,
  val pollenChip: PollenChipUiState?,
  val alert: HomeAlertUiState?
) {

  companion object {
    val Empty: AirQualityPresentation =
      AirQualityPresentation(
        airQualityChip = null,
        pollenChip = null,
        alert = null
      )
  }
}
