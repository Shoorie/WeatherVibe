package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.weekForecast

internal class DailyForecastListPreview :
  PreviewParameterProvider<DailyForecastsUiState> {

  override val values: Sequence<DailyForecastsUiState> =
    sequenceOf(weekForecast)
}
