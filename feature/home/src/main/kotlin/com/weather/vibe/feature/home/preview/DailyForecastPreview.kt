package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState

internal class DailyForecastPreview :
  PreviewParameterProvider<DailyForecastUiState> {

  private val warmDay: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = "⛅",
      dayLabel = "Tue",
      maxTemperature = "22°",
      minTemperature = "14°"
    )

  private val coldDay: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = "❄️",
      dayLabel = "Wed",
      maxTemperature = "-2°",
      minTemperature = "-10°"
    )

  override val values: Sequence<DailyForecastUiState> =
    sequenceOf(warmDay, coldDay)
}
