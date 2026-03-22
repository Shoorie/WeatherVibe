package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState

internal class HourlyForecastListPreview :
  PreviewParameterProvider<List<HourlyForecastUiState>> {

  private val eightHours: List<HourlyForecastUiState> =
    List(8) { index ->
      HourlyForecastUiState(
        conditionEmoji = listOf("⛅", "☁️", "🌤️", "☀️", "⛅", "🌧️", "☁️", "🌤️")[index],
        isCurrentHour = index == 0,
        temperature = "${18 + index}°",
        timeLabel = "${14 + index}:00"
      )
    }

  override val values: Sequence<List<HourlyForecastUiState>> =
    sequenceOf(eightHours)
}
