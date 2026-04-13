package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreviewData.afternoonSunInfo
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.eightHoursForecast
import com.weather.vibe.feature.home.preview.HomePreviewData.header
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist
import com.weather.vibe.feature.home.preview.HomePreviewData.warmDayCurrent
import com.weather.vibe.feature.home.preview.HomePreviewData.weekForecast
import com.weather.vibe.feature.home.ui.HomeResources.Texts.atmosphereSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.conditionsSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSectionTitle
import com.weather.vibe.feature.home.ui.component.details.DetailSection
import com.weather.vibe.feature.home.ui.component.sun.SunArcSection

@Composable
internal fun WeatherDetailsLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = Medium)
  ) {
    item { Spacer(modifier = Modifier.height(Small)) }
    item { SunArcSection(state = state.sunriseSunset) }
    item { Spacer(modifier = Modifier.height(Medium)) }
    item {
      DetailSection(
        title = windSectionTitle(),
        items = state.detailsSections.wind
      )
    }
    item { Spacer(modifier = Modifier.height(Medium)) }
    item {
      DetailSection(
        title = atmosphereSectionTitle(),
        items = state.detailsSections.atmosphere
      )
    }
    item { Spacer(modifier = Modifier.height(Medium)) }
    item {
      DetailSection(
        title = conditionsSectionTitle(),
        items = state.detailsSections.conditions
      )
    }
    item { Spacer(modifier = Modifier.height(ExtraLarge)) }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WeatherDetailsLoadedContent(
      state = Loaded(
        currentWeather = warmDayCurrent,
        dailyForecast = weekForecast,
        detailsSections = detailsSections,
        header = header,
        hourlyForecast = eightHoursForecast,
        playlist = loadedPlaylist,
        sunriseSunset = afternoonSunInfo
      )
    )
  }
}
