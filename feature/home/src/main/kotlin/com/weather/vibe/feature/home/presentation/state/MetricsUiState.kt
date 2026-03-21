package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class MetricsUiState(
  val cloudCoverValue: String,
  val dewPointValue: String,
  val humidityValue: String,
  val precipitationAmountValue: String,
  val precipitationValue: String,
  val pressureValue: String,
  val uvIndexValue: String,
  val visibilityValue: String,
  val windDirectionValue: String,
  val windGustsValue: String,
  val windSpeedMaxValue: String,
  val windSpeedValue: String
)
