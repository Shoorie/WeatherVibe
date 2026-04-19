package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.ui.HomeEmojis.cloud
import com.weather.vibe.feature.home.ui.HomeEmojis.compass
import com.weather.vibe.feature.home.ui.HomeEmojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeEmojis.gauge
import com.weather.vibe.feature.home.ui.HomeEmojis.humidity
import com.weather.vibe.feature.home.ui.HomeEmojis.wind
import com.weather.vibe.feature.home.ui.HomeEmojis.windGusts
import com.weather.vibe.feature.home.ui.HomeEmojis.windMax
import kotlinx.collections.immutable.toImmutableList

internal class DetailSectionPreview :
  PreviewParameterProvider<DetailSectionPreviewParams> {

  private val windSection: DetailSectionPreviewParams =
    DetailSectionPreviewParams(
      emoji = wind(),
      items = listOf(
        MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
        MetricItemUiState(compass(), "Direction", "SE"),
        MetricItemUiState(windGusts(), "Wind Gusts", "20 km/h"),
        MetricItemUiState(windMax(), "Max Wind", "28 km/h")
      ).toImmutableList(),
      subtitle = "Speed, direction and gusts",
      title = "Wind"
    )

  private val atmosphereSection: DetailSectionPreviewParams =
    DetailSectionPreviewParams(
      emoji = humidity(),
      items = listOf(
        MetricItemUiState(humidity(), "Humidity", "65%"),
        MetricItemUiState(gauge(), "Pressure", "1015 hPa"),
        MetricItemUiState(dewDrop(), "Dew Point", "12°"),
        MetricItemUiState(cloud(), "Cloud Cover", "45%")
      ).toImmutableList(),
      subtitle = "Pressure, humidity and dew point",
      title = "Atmosphere"
    )

  override val values: Sequence<DetailSectionPreviewParams> =
    sequenceOf(windSection, atmosphereSection)
}
