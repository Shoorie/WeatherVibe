package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.preview.WeatherMetricsPreviewParameterProvider
import com.weather.vibe.feature.home.preview.WeatherMetricsPreviewParams
import com.weather.vibe.feature.home.ui.HomeResources.Texts.directionLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.humidityLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.precipitationLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSpeedLabel
import kotlin.math.roundToInt

@Composable
internal fun WeatherMetricsGrid(
  modifier: Modifier = Modifier,
  humidity: Int,
  windSpeed: Double,
  windDirection: Double,
  precipitationProbability: Int
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
  ) {
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = "\uD83D\uDCA7",
      value = "$humidity%",
      label = humidityLabel()
    )
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = "\uD83D\uDCA8",
      value = "${windSpeed.roundToInt()} km/h",
      label = windSpeedLabel()
    )
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = "\uD83E\uDDED",
      value = windDirection.toCardinalDirection(),
      label = directionLabel()
    )
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = "\uD83C\uDF02",
      value = "$precipitationProbability%",
      label = precipitationLabel()
    )
  }
}

private fun Double.toCardinalDirection(): String {
  val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
  val index = ((this / 45.0) + 0.5).toInt() % 8
  return directions[index]
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WeatherMetricsPreviewParameterProvider::class)
  params: WeatherMetricsPreviewParams
) {
  WeatherVibeTheme {
    WeatherMetricsGrid(
      humidity = params.humidity,
      windSpeed = params.windSpeed,
      windDirection = params.windDirection,
      precipitationProbability = params.precipitationProbability
    )
  }
}
