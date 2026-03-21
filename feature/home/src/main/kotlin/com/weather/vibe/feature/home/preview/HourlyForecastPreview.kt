package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState

internal class HourlyForecastPreview :
  PreviewParameterProvider<HourlyForecastUiState> {

  private val currentHour: HourlyForecastUiState =
    HourlyForecastUiState(
      conditionEmoji = "⛅",
      isCurrentHour = true,
      temperature = "21°",
      timeLabel = "14:00"
    )

  private val regularHour: HourlyForecastUiState =
    HourlyForecastUiState(
      conditionEmoji = "🌙",
      isCurrentHour = false,
      temperature = "-3°",
      timeLabel = "02:00"
    )

  override val values: Sequence<HourlyForecastUiState> =
    sequenceOf(currentHour, regularHour)
}
