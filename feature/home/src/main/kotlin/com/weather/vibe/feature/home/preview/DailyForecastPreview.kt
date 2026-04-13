package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.snow

internal class DailyForecastPreview :
  PreviewParameterProvider<DailyForecastUiState> {

  private val warmDay: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = partlyCloudy(),
      conditionLabel = "Partly Cloudy",
      dayLabel = "Tue",
      isToday = true,
      maxTemperature = "22°",
      minTemperature = "14°",
      range = DailyRangeUiState(startFraction = 0.7f, endFraction = 0.95f, currentFraction = 0.85f)
    )

  private val coldDay: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = snow(),
      conditionLabel = "Snow",
      dayLabel = "Wed",
      isToday = false,
      maxTemperature = "-2°",
      minTemperature = "-10°",
      range = DailyRangeUiState(startFraction = 0.0f, endFraction = 0.25f)
    )

  override val values: Sequence<DailyForecastUiState> =
    sequenceOf(warmDay, coldDay)
}
