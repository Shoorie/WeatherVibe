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
import com.weather.vibe.feature.home.ui.HomeEmojis.cloud
import com.weather.vibe.feature.home.ui.HomeEmojis.humidity
import com.weather.vibe.feature.home.ui.HomeEmojis.wind
import com.weather.vibe.feature.home.ui.HomeForecastTexts.atmosphereSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeForecastTexts.atmosphereSectionTitle
import com.weather.vibe.feature.home.ui.HomeForecastTexts.conditionsSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeForecastTexts.conditionsSectionTitle
import com.weather.vibe.feature.home.ui.HomeForecastTexts.windSectionSubtitle
import com.weather.vibe.feature.home.ui.HomeForecastTexts.windSectionTitle
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.ATMOSPHERE
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.ATMOSPHERE_SPACER
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.BOTTOM_SPACER
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.CONDITIONS
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.CONDITIONS_SPACER
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.SUN
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.TOP_SPACER
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.WIND
import com.weather.vibe.feature.home.ui.WeatherDetailsKeys.WIND_SPACER
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
    item(key = TOP_SPACER) { Spacer(modifier = Modifier.height(Small)) }
    item(key = SUN) { SunArcSection(state = state.forecast.sunriseSunset) }
    item(key = WIND_SPACER) { Spacer(modifier = Modifier.height(Medium)) }
    item(key = WIND) {
      DetailSection(
        emoji = wind(),
        title = windSectionTitle(),
        subtitle = windSectionSubtitle(),
        items = state.details.wind
      )
    }
    item(key = ATMOSPHERE_SPACER) { Spacer(modifier = Modifier.height(Medium)) }
    item(key = ATMOSPHERE) {
      DetailSection(
        emoji = humidity(),
        title = atmosphereSectionTitle(),
        subtitle = atmosphereSectionSubtitle(),
        items = state.details.atmosphere
      )
    }
    item(key = CONDITIONS_SPACER) { Spacer(modifier = Modifier.height(Medium)) }
    item(key = CONDITIONS) {
      DetailSection(
        emoji = cloud(),
        title = conditionsSectionTitle(),
        subtitle = conditionsSectionSubtitle(),
        items = state.details.conditions
      )
    }
    item(key = BOTTOM_SPACER) { Spacer(modifier = Modifier.height(ExtraLarge)) }
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
