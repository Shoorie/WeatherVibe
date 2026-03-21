package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.HourlyItemHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.HourlyItemWidth
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.preview.HourlyForecastPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.nowLabel

@Composable
internal fun HourlyForecastItem(
  modifier: Modifier = Modifier,
  state: HourlyForecastUiState
) {
  Column(
    modifier = modifier
      .width(HourlyItemWidth)
      .height(HourlyItemHeight),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(
      text = if (state.isCurrentHour) nowLabel() else state.timeLabel,
      style = typography.labelSmall,
      color = if (state.isCurrentHour) colors.accent
        else colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
    Text(
      text = state.conditionEmoji,
      fontSize = EmojiSizeMedium
    )
    Text(
      text = state.temperature,
      style = typography.bodyMedium,
      color = colors.onBackground,
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
