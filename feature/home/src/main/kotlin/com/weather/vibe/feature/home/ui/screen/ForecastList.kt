package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreviewData.afternoonSunInfo
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.eightHoursForecast
import com.weather.vibe.feature.home.preview.HomePreviewData.header
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist
import com.weather.vibe.feature.home.preview.HomePreviewData.pleasantVibe
import com.weather.vibe.feature.home.preview.HomePreviewData.warmDayCurrent
import com.weather.vibe.feature.home.preview.HomePreviewData.weekForecast
import com.weather.vibe.feature.home.ui.HomeKeys.BRIEFING
import com.weather.vibe.feature.home.ui.HomeKeys.DAILY
import com.weather.vibe.feature.home.ui.HomeKeys.DAILY_VIBE
import com.weather.vibe.feature.home.ui.HomeKeys.DETAILS
import com.weather.vibe.feature.home.ui.HomeKeys.HERO
import com.weather.vibe.feature.home.ui.HomeKeys.HOURLY
import com.weather.vibe.feature.home.ui.HomeTestTags.FORECAST_LIST
import com.weather.vibe.feature.home.ui.component.briefing.WeatherBriefingCard
import com.weather.vibe.feature.home.ui.component.daily.DailyForecastList
import com.weather.vibe.feature.home.ui.component.details.DetailsPreviewCard
import com.weather.vibe.feature.home.ui.component.hero.HomeHeroCard
import com.weather.vibe.feature.home.ui.component.hourly.HourlyForecastRow
import com.weather.vibe.feature.home.ui.component.vibe.DailyVibeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ForecastList(
  modifier: Modifier = Modifier,
  state: Loaded,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onMusicClick: () -> Unit
) {

  val horizontalPadding = remember { Modifier.padding(horizontal = Medium) }
  val listContentPadding = remember {
    PaddingValues(top = Medium, bottom = ExtraLarge)
  }

  PullToRefreshBox(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding(),
    isRefreshing = state.isRefreshing,
    onRefresh = onRefresh
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag(FORECAST_LIST),
      contentPadding = listContentPadding,
      verticalArrangement = Arrangement.spacedBy(Large)
    ) {
      item(key = HERO) {
        HomeHeroCard(
          modifier = horizontalPadding,
          header = state.header,
          currentWeather = state.currentWeather,
          onNavigateToSearch = onNavigateToSearch,
          onNavigateToSettings = onNavigateToSettings
        )
      }
      if (state.dailyVibe != null) {
        item(key = DAILY_VIBE) {
          DailyVibeCard(
            modifier = horizontalPadding,
            state = state.dailyVibe
          )
        }
      }
      item(key = BRIEFING) {
        WeatherBriefingCard(
          modifier = horizontalPadding,
          onMusicClick = onMusicClick,
          onRetryClick = onRetrySuggestion,
          state = state.briefing
        )
      }
      item(key = HOURLY) {
        HourlyForecastRow(state = state.hourlyForecast)
      }
      item(key = DAILY) {
        DailyForecastList(
          modifier = horizontalPadding,
          state = state.dailyForecast
        )
      }
      item(key = DETAILS) {
        DetailsPreviewCard(
          modifier = horizontalPadding,
          previewItems = state.detailsSections.previewItems,
          onClick = onNavigateToDetails
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ForecastList(
      state = Loaded(
        currentWeather = warmDayCurrent,
        dailyForecast = weekForecast,
        dailyVibe = pleasantVibe,
        detailsSections = detailsSections,
        header = header,
        hourlyForecast = eightHoursForecast,
        playlist = loadedPlaylist,
        sunriseSunset = afternoonSunInfo
      ),
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {},
      onRefresh = {},
      onRetrySuggestion = {},
      onMusicClick = {}
    )
  }
}
