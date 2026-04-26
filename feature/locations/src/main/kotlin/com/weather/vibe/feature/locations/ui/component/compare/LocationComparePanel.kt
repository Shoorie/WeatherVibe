package com.weather.vibe.feature.locations.ui.component.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.location.model.LocationWeatherAdvantage
import com.weather.vibe.domain.location.model.LocationWeatherAdvantage.FirstLocation
import com.weather.vibe.domain.location.model.LocationWeatherAdvantage.SecondLocation
import com.weather.vibe.feature.locations.presentation.state.LocationComparePairUiState
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUiState
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsEmojis
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareFeelsLike
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.metricHumidity
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.metricRain
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.metricTemperature
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.metricWind
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.valueHumidity
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.valueRain
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.valueTemperature
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.valueWind

@Composable
internal fun LocationComparePanel(
  modifier: Modifier = Modifier,
  pair: LocationComparePairUiState
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.glassSurface)
      .border(
        width = Border,
        color = colors.outlineVariant,
        shape = shapes.card
      )
      .padding(Medium),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    CompareHeader(first = pair.first, second = pair.second)
    HorizontalDivider(color = colors.outlineVariant)
    LocationCompareTemperatureChart(
      first = pair.first,
      second = pair.second,
      axis = pair.temperatureAxis
    )
    HorizontalDivider(color = colors.outlineVariant)
    MetricBreakdown(pair = pair)
  }
}

@Composable
private fun MetricBreakdown(pair: LocationComparePairUiState) {
  TemperatureMetric(pair = pair)
  WindMetric(pair = pair)
  HumidityMetric(pair = pair)
  RainMetric(pair = pair)
}

@Composable
private fun TemperatureMetric(pair: LocationComparePairUiState) {
  MetricRow(
    emoji = LocationsEmojis.metricTemperature(),
    label = metricTemperature(),
    valueFirst = valueTemperature(pair.first.temperature),
    valueSecond = valueTemperature(pair.second.temperature),
    winnerSide = pair.winners.temperature
  )
}

@Composable
private fun WindMetric(pair: LocationComparePairUiState) {
  MetricRow(
    emoji = LocationsEmojis.metricWind(),
    label = metricWind(),
    valueFirst = valueWind(pair.first.windKph),
    valueSecond = valueWind(pair.second.windKph),
    winnerSide = pair.winners.wind
  )
}

@Composable
private fun HumidityMetric(pair: LocationComparePairUiState) {
  MetricRow(
    emoji = LocationsEmojis.metricHumidity(),
    label = metricHumidity(),
    valueFirst = valueHumidity(pair.first.humidityPercent),
    valueSecond = valueHumidity(pair.second.humidityPercent),
    winnerSide = pair.winners.humidity
  )
}

@Composable
private fun RainMetric(pair: LocationComparePairUiState) {
  MetricRow(
    emoji = LocationsEmojis.metricRain(),
    label = metricRain(),
    valueFirst = valueRain(pair.first.precipitationChancePercent),
    valueSecond = valueRain(pair.second.precipitationChancePercent),
    winnerSide = pair.winners.rain
  )
}

@Composable
private fun CompareHeader(
  first: LocationCompareUiState,
  second: LocationCompareUiState
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    HeaderSide(
      modifier = Modifier.weight(1f),
      side = first,
      alignment = Alignment.Start,
      textAlign = TextAlign.Start
    )
    Text(
      text = LocationsEmojis.versus(),
      style = typography.titleMedium,
      color = colors.textTertiary
    )
    HeaderSide(
      modifier = Modifier.weight(1f),
      side = second,
      alignment = Alignment.End,
      textAlign = TextAlign.End
    )
  }
}

@Composable
private fun HeaderSide(
  modifier: Modifier = Modifier,
  side: LocationCompareUiState,
  alignment: Alignment.Horizontal,
  textAlign: TextAlign
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall),
    horizontalAlignment = alignment
  ) {
    Text(
      text = side.weather.emoji,
      style = typography.displaySmall
    )
    Text(
      text = side.card.name,
      style = typography.titleSmall,
      color = colors.onBackground,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      textAlign = textAlign
    )
    Text(
      text = compareFeelsLike(side.feelsLike),
      style = typography.labelSmall,
      color = colors.textTertiary,
      textAlign = textAlign
    )
  }
}

@Composable
private fun MetricRow(
  emoji: String,
  label: String,
  valueFirst: String,
  valueSecond: String,
  winnerSide: LocationWeatherAdvantage
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    MetricValue(
      modifier = Modifier.weight(1f),
      text = valueFirst,
      textAlign = TextAlign.Start,
      color = valueColor(isWinner = winnerSide == FirstLocation)
    )
    MetricLabel(
      modifier = Modifier.weight(LocationsDefaults.MetricLabelWeight),
      emoji = emoji,
      label = label
    )
    MetricValue(
      modifier = Modifier.weight(1f),
      text = valueSecond,
      textAlign = TextAlign.End,
      color = valueColor(isWinner = winnerSide == SecondLocation)
    )
  }
}

@Composable
private fun MetricValue(
  modifier: Modifier,
  text: String,
  textAlign: TextAlign,
  color: Color
) {
  Text(
    modifier = modifier,
    text = text,
    style = typography.titleMedium.copy(fontWeight = SemiBold),
    color = color,
    textAlign = textAlign,
    maxLines = 1
  )
}

@Composable
private fun MetricLabel(
  modifier: Modifier,
  emoji: String,
  label: String
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = emoji,
      style = typography.bodyMedium
    )
    Text(
      text = label,
      style = typography.labelSmall,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}

@Composable
private fun valueColor(isWinner: Boolean): Color =
  if (isWinner) colors.accent else colors.textTertiary

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationComparePanel(
      modifier = Modifier
        .fillMaxWidth()
        .padding(Medium),
      pair = LocationsPreviewData.comparePair
    )
  }
}
