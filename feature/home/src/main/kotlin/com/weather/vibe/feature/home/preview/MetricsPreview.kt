package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.compass
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.eye
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.gauge
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.precipitation
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windGusts
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windMax

internal class MetricsPreview :
  PreviewParameterProvider<MetricsUiState> {

  private val mildWeather: MetricsUiState =
    MetricsUiState(
      items = listOf(
        MetricItemUiState(humidity(), "Humidity", "65%"),
        MetricItemUiState(wind(), "Wind Speed", "15 km/h"),
        MetricItemUiState(compass(), "Direction", "SW"),
        MetricItemUiState(precipitation(), "Precipitation", "20%"),
        MetricItemUiState(uvIndex(), "UV Index", "3.5"),
        MetricItemUiState(cloud(), "Cloud Cover", "45%"),
        MetricItemUiState(gauge(), "Pressure", "1013 hPa"),
        MetricItemUiState(eye(), "Visibility", "24 km"),
        MetricItemUiState(dewDrop(), "Dew Point", "12\u00B0"),
        MetricItemUiState(windGusts(), "Wind Gusts", "25 km/h"),
        MetricItemUiState(windMax(), "Max Wind", "32 km/h"),
        MetricItemUiState(rainfall(), "Rainfall", "0.0 mm")
      )
    )

  private val stormyWeather: MetricsUiState =
    MetricsUiState(
      items = listOf(
        MetricItemUiState(humidity(), "Humidity", "90%"),
        MetricItemUiState(wind(), "Wind Speed", "35 km/h"),
        MetricItemUiState(compass(), "Direction", "N"),
        MetricItemUiState(precipitation(), "Precipitation", "85%"),
        MetricItemUiState(uvIndex(), "UV Index", "8.2"),
        MetricItemUiState(cloud(), "Cloud Cover", "95%"),
        MetricItemUiState(gauge(), "Pressure", "998 hPa"),
        MetricItemUiState(eye(), "Visibility", "5 km"),
        MetricItemUiState(dewDrop(), "Dew Point", "18\u00B0"),
        MetricItemUiState(windGusts(), "Wind Gusts", "60 km/h"),
        MetricItemUiState(windMax(), "Max Wind", "48 km/h"),
        MetricItemUiState(rainfall(), "Rainfall", "12.4 mm")
      )
    )

  override val values: Sequence<MetricsUiState> =
    sequenceOf(mildWeather, stormyWeather)
}
