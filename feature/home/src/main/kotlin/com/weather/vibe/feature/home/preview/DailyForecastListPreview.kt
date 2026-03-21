package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState

internal class DailyForecastListPreview :
  PreviewParameterProvider<List<DailyForecastUiState>> {

  private val weekForecast: List<DailyForecastUiState> =
    listOf(
      DailyForecastUiState("⛅", "Today", "22°", "14°"),
      DailyForecastUiState("🌧️", "Tue", "19°", "11°"),
      DailyForecastUiState("☁️", "Wed", "15°", "8°"),
      DailyForecastUiState("☀️", "Thu", "24°", "16°"),
      DailyForecastUiState("🌤️", "Fri", "21°", "13°"),
      DailyForecastUiState("🌦️", "Sat", "17°", "10°"),
      DailyForecastUiState("⛅", "Sun", "20°", "12°")
    )

  override val values: Sequence<List<DailyForecastUiState>> =
    sequenceOf(weekForecast)
}
