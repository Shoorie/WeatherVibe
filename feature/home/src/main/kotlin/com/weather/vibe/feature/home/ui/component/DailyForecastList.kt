package com.weather.vibe.feature.home.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.card.GlassCardSection
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.preview.DailyForecastListPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.dailyForecastTitle

@Composable
internal fun DailyForecastList(
  modifier: Modifier = Modifier,
  dailyForecasts: List<DailyForecastUiState>
) {
  GlassCardSection(
    modifier = modifier,
    title = dailyForecastTitle()
  ) {
    dailyForecasts.forEachIndexed { index, daily ->
      DailyForecastItem(state = daily)
      if (index < dailyForecasts.lastIndex) {
        HorizontalDivider(color = colors.outline)
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyForecastListPreview::class)
  forecasts: List<DailyForecastUiState>
) {
  WeatherVibeTheme {
    DailyForecastList(dailyForecasts = forecasts)
  }
}
