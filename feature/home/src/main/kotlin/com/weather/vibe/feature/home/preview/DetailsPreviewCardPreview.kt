package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.precipitation
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind

internal class DetailsPreviewCardPreview :
  PreviewParameterProvider<List<MetricItemUiState>> {

  private val defaultItems: List<MetricItemUiState> =
    listOf(
      MetricItemUiState(humidity(), "Humidity", "65%"),
      MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
      MetricItemUiState(uvIndex(), "UV Index", "3.5"),
      MetricItemUiState(precipitation(), "Precipitation", "20%")
    )

  override val values: Sequence<List<MetricItemUiState>> =
    sequenceOf(defaultItems)
}
