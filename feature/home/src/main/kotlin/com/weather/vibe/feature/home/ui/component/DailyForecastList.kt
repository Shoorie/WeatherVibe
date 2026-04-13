package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.preview.DailyForecastListPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.dailyForecastTitle

@Composable
internal fun DailyForecastList(
  modifier: Modifier = Modifier,
  state: DailyForecastsUiState
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = dailyForecastTitle(),
    uppercase = true
  ) {
    state.items.forEachIndexed { index, daily ->
      key(daily.dayLabel) {
        DailyForecastItem(state = daily)
        if (index < state.items.lastIndex) {
          HorizontalDivider(color = colors.outlineVariant)
        }
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyForecastListPreview::class)
  state: DailyForecastsUiState
) {
  WeatherVibeTheme {
    DailyForecastList(state = state)
  }
}
