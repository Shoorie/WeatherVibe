package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class MetricPreviewParameterProvider :
  PreviewParameterProvider<MetricPreviewParams> {

  private val humidity = MetricPreviewParams(
    icon = "💧",
    label = "Humidity",
    value = "65%"
  )

  private val windSpeed = MetricPreviewParams(
    icon = "💨",
    label = "Wind Speed",
    value = "14 km/h"
  )

  override val values: Sequence<MetricPreviewParams> = sequenceOf(
    humidity,
    windSpeed
  )
}
