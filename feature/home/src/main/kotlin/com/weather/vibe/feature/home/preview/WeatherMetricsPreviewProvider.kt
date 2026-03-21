package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class WeatherMetricsPreviewParameterProvider :
  PreviewParameterProvider<WeatherMetricsPreviewParams> {

  private val mildWeather: WeatherMetricsPreviewParams =
    WeatherMetricsPreviewParams(
      humidity = 65,
      precipitationProbability = 20,
      windDirection = 225.0,
      windSpeed = 14.5
    )

  private val stormyWeather: WeatherMetricsPreviewParams =
    WeatherMetricsPreviewParams(
      humidity = 90,
      precipitationProbability = 85,
      windDirection = 0.0,
      windSpeed = 35.0
    )

  override val values: Sequence<WeatherMetricsPreviewParams> =
    sequenceOf(mildWeather, stormyWeather)
}
