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
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.forecastSection
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind
import com.weather.vibe.feature.home.ui.HomeResources.Texts.atmosphereSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.atmosphereSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.conditionsSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.conditionsSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSectionSubtitle
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
    item { SunArcSection(state = state.forecast.sunriseSunset) }
    item { Spacer(modifier = Modifier.height(Medium)) }
    item {
      DetailSection(
        emoji = wind(),
        title = windSectionTitle(),
        subtitle = windSectionSubtitle(),
        items = state.details.wind
      )
    }
    item { Spacer(modifier = Modifier.height(Medium)) }
    item {
      DetailSection(
        emoji = humidity(),
        title = atmosphereSectionTitle(),
        subtitle = atmosphereSectionSubtitle(),
        items = state.details.atmosphere
      )
    }
    item { Spacer(modifier = Modifier.height(Medium)) }
    item {
      DetailSection(
        emoji = cloud(),
        title = conditionsSectionTitle(),
        subtitle = conditionsSectionSubtitle(),
        items = state.details.conditions
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
        details = detailsSections,
        forecast = forecastSection
      )
    )
  }
}
