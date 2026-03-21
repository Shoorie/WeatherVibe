package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState

internal class CurrentWeatherPreview :
  PreviewParameterProvider<CurrentWeatherUiState> {

  private val warmDay: CurrentWeatherUiState =
    CurrentWeatherUiState(
      conditionEmoji = "⛅",
      conditionLabel = "Partly Cloudy",
      currentTemperature = "19°",
      feelsLikeTemperature = "17°",
      highTemperature = "22°",
      lowTemperature = "14°"
    )

  private val coldNight: CurrentWeatherUiState =
    CurrentWeatherUiState(
      conditionEmoji = "❄️",
      conditionLabel = "Snow",
      currentTemperature = "-5°",
      feelsLikeTemperature = "-9°",
      highTemperature = "0°",
      lowTemperature = "-8°"
    )

  override val values: Sequence<CurrentWeatherUiState> =
    sequenceOf(warmDay, coldNight)
}
