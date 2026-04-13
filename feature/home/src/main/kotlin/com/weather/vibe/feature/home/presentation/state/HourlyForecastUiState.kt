package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class HourlyForecastUiState(
  val conditionEmoji: String,
  val isCurrentHour: Boolean,
  val temperature: String,
  val timeLabel: String
) {

  val contentDescription: String =
    "$timeLabel, $temperature"
}
