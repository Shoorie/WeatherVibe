package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.preview.params.MetricPreviewParams

internal class MetricPreview :
  PreviewParameterProvider<MetricPreviewParams> {

  private val humidity: MetricPreviewParams =
    MetricPreviewParams(
      icon = "💧",
      label = "Humidity",
      value = "65%"
    )

  private val windSpeed: MetricPreviewParams =
    MetricPreviewParams(
      icon = "💨",
      label = "Wind Speed",
      value = "14 km/h"
    )

  override val values: Sequence<MetricPreviewParams> =
    sequenceOf(humidity, windSpeed)
}
