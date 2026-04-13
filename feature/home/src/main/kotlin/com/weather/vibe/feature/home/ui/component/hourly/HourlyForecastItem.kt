package com.weather.vibe.feature.home.ui.component.hourly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.preview.HourlyForecastPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMedium
import com.weather.vibe.feature.home.ui.HomeDefaults.HourlyItemHeight
import com.weather.vibe.feature.home.ui.HomeDefaults.HourlyItemWidth
import com.weather.vibe.feature.home.ui.HomeTextStyles.hourlyBackgroundColor
import com.weather.vibe.feature.home.ui.HomeTextStyles.hourlyTemperatureColor
import com.weather.vibe.feature.home.ui.HomeTextStyles.hourlyTimeColor

@Composable
internal fun HourlyForecastItem(
  modifier: Modifier = Modifier,
  state: HourlyForecastUiState
) {
  Column(
    modifier = modifier
      .width(HourlyItemWidth)
      .height(HourlyItemHeight)
      .clip(shapes.pill)
      .background(hourlyBackgroundColor(state.isCurrentHour))
      .clearAndSetSemantics { contentDescription = state.contentDescription },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(
      text = state.timeLabel,
      style = typography.labelSmall,
      color = hourlyTimeColor(state.isCurrentHour),
      textAlign = TextAlign.Center
    )
    Text(
      text = state.conditionEmoji,
      fontSize = EmojiMedium
    )
    Text(
      text = state.temperature,
      style = typography.bodyMedium,
      color = hourlyTemperatureColor(state.isCurrentHour),
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
