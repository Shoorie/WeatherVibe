package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeEmojis.humidity
import com.weather.vibe.feature.home.ui.HomeEmojis.precipitation
import com.weather.vibe.feature.home.ui.HomeEmojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeEmojis.wind
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal class DetailsPreviewCardPreview :
  PreviewParameterProvider<ImmutableList<MetricItemUiState>> {

  private val defaultItems: ImmutableList<MetricItemUiState> =
    persistentListOf(
      MetricItemUiState(humidity(), "Humidity", "65%"),
      MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
      MetricItemUiState(uvIndex(), "UV Index", "3.5"),
      MetricItemUiState(precipitation(), "Precipitation", "20%")
    )

  override val values: Sequence<ImmutableList<MetricItemUiState>> =
    sequenceOf(defaultItems)
}
