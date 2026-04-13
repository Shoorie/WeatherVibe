package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.preview.HourlyForecastPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMedium
import com.weather.vibe.feature.home.ui.HomeDefaults.HourlyItemHeight
import com.weather.vibe.feature.home.ui.HomeDefaults.HourlyItemWidth
import com.weather.vibe.feature.home.ui.HomeResources.Texts.nowLabel

@Composable
internal fun HourlyForecastItem(
  modifier: Modifier = Modifier,
  state: HourlyForecastUiState
) {

  val currentHourLabel = nowLabel()
  val timeLabel = if (state.isCurrentHour) currentHourLabel else state.timeLabel
  val description = remember(timeLabel, state.temperature) {
    "$timeLabel, ${state.temperature}"
  }
  val timeColor = if (state.isCurrentHour) colors.onAccent else colors.onSurfaceVariant
  val temperatureColor = if (state.isCurrentHour) colors.onAccent else colors.onBackground
  val accentColor = colors.accent
  val highlightModifier = remember(state.isCurrentHour, accentColor) {
    if (state.isCurrentHour) Modifier.background(accentColor) else Modifier
  }

  Column(
    modifier = modifier
      .width(HourlyItemWidth)
      .height(HourlyItemHeight)
      .clip(shapes.pill)
      .then(highlightModifier)
      .clearAndSetSemantics { contentDescription = description },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(
      text = timeLabel,
      style = typography.labelSmall,
      color = timeColor,
      textAlign = TextAlign.Center
    )
    Text(
      text = state.conditionEmoji,
      fontSize = EmojiMedium
    )
    Text(
      text = state.temperature,
      style = typography.bodyMedium,
      color = temperatureColor,
      textAlign = TextAlign.Center
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HourlyForecastPreview::class)
  state: HourlyForecastUiState
) {
  WeatherVibeTheme {
    HourlyForecastItem(state = state)
  }
}
