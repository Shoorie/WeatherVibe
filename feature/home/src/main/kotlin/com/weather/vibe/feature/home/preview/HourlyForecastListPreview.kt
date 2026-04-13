package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.eightHoursForecast

internal class HourlyForecastListPreview :
  PreviewParameterProvider<HourlyForecastsUiState> {

  override val values: Sequence<HourlyForecastsUiState> =
    sequenceOf(eightHoursForecast)
}
