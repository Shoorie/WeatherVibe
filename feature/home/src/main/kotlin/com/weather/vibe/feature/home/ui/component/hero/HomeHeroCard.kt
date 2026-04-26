package com.weather.vibe.feature.home.ui.component.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
  val accent = colors.accent
  val accentDark = colors.accentDark
  val gradient = remember(accent, accentDark) {
    Brush.linearGradient(colors = listOf(accent, accentDark))
  }
  Box(
    modifier = modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {}
      .shadow(elevation = Card, shape = shapes.cardLarge, clip = false)
      .clip(shapes.cardLarge)
      .background(gradient)
      .padding(Large)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
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
