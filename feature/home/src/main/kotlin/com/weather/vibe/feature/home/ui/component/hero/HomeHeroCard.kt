package com.weather.vibe.feature.home.ui.component.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation.Card
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.header
import com.weather.vibe.feature.home.preview.HomePreviewData.warmDayCurrent

@Composable
internal fun HomeHeroCard(
  modifier: Modifier = Modifier,
  header: HeaderUiState,
  currentWeather: CurrentWeatherUiState,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .shadow(elevation = Card, shape = shapes.cardLarge, clip = false)
      .clip(shapes.cardLarge)
      .background(colors.accent)
      .padding(Large)
      .semantics(mergeDescendants = true) {},
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    HeroHeader(
      header = header,
      onNavigateToSearch = onNavigateToSearch,
      onNavigateToSettings = onNavigateToSettings
    )
    HeroWeather(state = currentWeather)
    HeroTempChips(
      highTemperature = currentWeather.highTemperature,
      lowTemperature = currentWeather.lowTemperature
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    HomeHeroCard(
      header = header,
      currentWeather = warmDayCurrent,
      onNavigateToSearch = {},
      onNavigateToSettings = {}
    )
  }
}
