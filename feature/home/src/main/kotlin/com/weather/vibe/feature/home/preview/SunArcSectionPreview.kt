package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState

internal class SunArcSectionPreview :
  PreviewParameterProvider<SunriseSunsetUiState> {

  private val afternoon: SunriseSunsetUiState =
    SunriseSunsetUiState(
      dayLength = "11h 43m",
      sunProgress = 0.65f,
      sunriseTime = "06:24",
      sunsetTime = "18:07"
    )

  private val nighttime: SunriseSunsetUiState =
    SunriseSunsetUiState(
      dayLength = "11h 43m",
      sunProgress = 0f,
      sunriseTime = "06:24",
      sunsetTime = "18:07"
    )

  override val values: Sequence<SunriseSunsetUiState> =
    sequenceOf(afternoon, nighttime)
}
