package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.compass
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.gauge
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windGusts
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windMax

internal class DetailSectionPreview :
  PreviewParameterProvider<DetailSectionPreviewParams> {

  private val windSection: DetailSectionPreviewParams =
    DetailSectionPreviewParams(
      items = listOf(
        MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
        MetricItemUiState(compass(), "Direction", "SW"),
        MetricItemUiState(windGusts(), "Wind Gusts", "20 km/h"),
        MetricItemUiState(windMax(), "Max Wind", "28 km/h")
      ),
      title = "Wind"
    )

  private val atmosphereSection: DetailSectionPreviewParams =
    DetailSectionPreviewParams(
      items = listOf(
        MetricItemUiState(humidity(), "Humidity", "65%"),
        MetricItemUiState(gauge(), "Pressure", "1015 hPa"),
        MetricItemUiState(dewDrop(), "Dew Point", "12°"),
        MetricItemUiState(cloud(), "Cloud Cover", "45%")
      ),
      title = "Atmosphere"
    )

  override val values: Sequence<DetailSectionPreviewParams> =
    sequenceOf(windSection, atmosphereSection)
}
