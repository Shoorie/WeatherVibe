package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.MetricsUiState

internal class MetricsPreview :
  PreviewParameterProvider<MetricsUiState> {

  private val mildWeather: MetricsUiState =
    MetricsUiState(
      cloudCoverValue = "45%",
      dewPointValue = "12°",
      humidityValue = "65%",
      precipitationAmountValue = "0.0 mm",
      precipitationValue = "20%",
      pressureValue = "1013 hPa",
      uvIndexValue = "3.5",
      visibilityValue = "24 km",
      windDirectionValue = "SW",
      windGustsValue = "25 km/h",
      windSpeedMaxValue = "32 km/h",
      windSpeedValue = "15 km/h"
    )

  private val stormyWeather: MetricsUiState =
    MetricsUiState(
      cloudCoverValue = "95%",
      dewPointValue = "18°",
      humidityValue = "90%",
      precipitationAmountValue = "12.4 mm",
      precipitationValue = "85%",
      pressureValue = "998 hPa",
      uvIndexValue = "8.2",
      visibilityValue = "5 km",
      windDirectionValue = "N",
      windGustsValue = "60 km/h",
      windSpeedMaxValue = "48 km/h",
      windSpeedValue = "35 km/h"
    )

  override val values: Sequence<MetricsUiState> =
    sequenceOf(mildWeather, stormyWeather)
}
