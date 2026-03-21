package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.preview.MetricsPreview
import com.weather.vibe.feature.home.ui.HomeResources.Emojis
import com.weather.vibe.feature.home.ui.HomeResources.Texts.cloudCoverLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.dewPointLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.directionLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.humidityLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.precipitationLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.pressureLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.rainfallLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.uvIndexLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.visibilityLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windGustsLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSpeedLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSpeedMaxLabel

// TODO [azalewski on 21/03/2026]: This composition could be implemented using
//  a Grid component.
@Composable
internal fun WeatherMetricsGrid(
  modifier: Modifier = Modifier,
  state: MetricsUiState
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(PaddingSmall)
  ) {
    MetricsRow(
      icon1 = Emojis.humidity(),
      value1 = state.humidityValue,
      label1 = humidityLabel(),
      icon2 = Emojis.wind(),
      value2 = state.windSpeedValue,
      label2 = windSpeedLabel(),
      icon3 = Emojis.compass(),
      value3 = state.windDirectionValue,
      label3 = directionLabel()
    )
    MetricsRow(
      icon1 = Emojis.precipitation(),
      value1 = state.precipitationValue,
      label1 = precipitationLabel(),
      icon2 = Emojis.uvIndex(),
      value2 = state.uvIndexValue,
      label2 = uvIndexLabel(),
      icon3 = Emojis.cloud(),
      value3 = state.cloudCoverValue,
      label3 = cloudCoverLabel()
    )
    MetricsRow(
      icon1 = Emojis.gauge(),
      value1 = state.pressureValue,
      label1 = pressureLabel(),
      icon2 = Emojis.eye(),
      value2 = state.visibilityValue,
      label2 = visibilityLabel(),
      icon3 = Emojis.dewDrop(),
      value3 = state.dewPointValue,
      label3 = dewPointLabel()
    )
    MetricsRow(
      icon1 = Emojis.windGusts(),
      value1 = state.windGustsValue,
      label1 = windGustsLabel(),
      icon2 = Emojis.windMax(),
      value2 = state.windSpeedMaxValue,
      label2 = windSpeedMaxLabel(),
      icon3 = Emojis.rainfall(),
      value3 = state.precipitationAmountValue,
      label3 = rainfallLabel()
    )
  }
}

@Composable
private fun MetricsRow(
  modifier: Modifier = Modifier,
  icon1: String,
  value1: String,
  label1: String,
  icon2: String,
  value2: String,
  label2: String,
  icon3: String,
  value3: String,
  label3: String
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
  ) {
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = icon1,
      value = value1,
      label = label1
    )
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = icon2,
      value = value2,
      label = label2
    )
    WeatherMetricCard(
      modifier = Modifier.weight(1f),
      icon = icon3,
      value = value3,
      label = label3
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(MetricsPreview::class)
  state: MetricsUiState
) {
  WeatherVibeTheme {
    WeatherMetricsGrid(state = state)
  }
}
