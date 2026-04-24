package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreviewData.aiSuggestionSection
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.forecastSection
import com.weather.vibe.feature.home.preview.HomePreviewData.pleasantDailyVibeCard
import com.weather.vibe.feature.home.preview.HomePreviewData.smogAlert
import com.weather.vibe.feature.home.ui.HomeKeys.ACTIVITY_PLANNER
import com.weather.vibe.feature.home.ui.HomeKeys.ALERT
import com.weather.vibe.feature.home.ui.HomeKeys.BRIEFING
import com.weather.vibe.feature.home.ui.HomeKeys.DAILY
import com.weather.vibe.feature.home.ui.HomeKeys.DAILY_VIBE
import com.weather.vibe.feature.home.ui.HomeKeys.DETAILS
import com.weather.vibe.feature.home.ui.HomeKeys.HERO
import com.weather.vibe.feature.home.ui.HomeKeys.HOURLY
import com.weather.vibe.feature.home.ui.HomeKeys.RATING_CARD
import com.weather.vibe.feature.viberating.ui.rating.RatingCardHost
import com.weather.vibe.feature.home.ui.HomeTestTags.FORECAST_LIST
import com.weather.vibe.feature.home.ui.component.activityplanner.ActivityPlannerTeaserCard
import com.weather.vibe.feature.home.ui.component.alert.HomeAlertBanner
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
  onNavigateToActivityPlanner: () -> Unit,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onNavigateToVibeHistory: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onMusicClick: () -> Unit,
  onShareClick: () -> Unit
) {

  val canShare = state.aiSuggestion.briefing is BriefingUiState.Loaded
  val horizontalPadding = remember { Modifier.padding(horizontal = Medium) }
  val listContentPadding = remember {
    PaddingValues(
      top = Medium,
      bottom = ExtraLarge
    )
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
      forecastItems(
        state = state,
        canShare = canShare,
        horizontalPadding = horizontalPadding,
        onNavigateToActivityPlanner = onNavigateToActivityPlanner,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToVibeHistory = onNavigateToVibeHistory,
        onRetrySuggestion = onRetrySuggestion,
        onMusicClick = onMusicClick,
        onShareClick = onShareClick
      )
    }
  }
}

@Suppress("LongParameterList", "LongMethod")
private fun LazyListScope.forecastItems(
  state: Loaded,
  canShare: Boolean,
  horizontalPadding: Modifier,
  onNavigateToActivityPlanner: () -> Unit,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onNavigateToVibeHistory: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onMusicClick: () -> Unit,
  onShareClick: () -> Unit
) {
  item(key = HERO) {
    HomeHeroCard(
      modifier = horizontalPadding,
      header = state.forecast.header,
      currentWeather = state.forecast.currentWeather,
      onNavigateToSearch = onNavigateToSearch,
      onNavigateToSettings = onNavigateToSettings
    )
  }
  if (state.alert != null) {
    item(key = ALERT) {
      HomeAlertBanner(
        modifier = horizontalPadding,
        state = state.alert
      )
    }
  }
  if (state.dailyVibe != null) {
    item(key = DAILY_VIBE) {
      DailyVibeCard(
        modifier = horizontalPadding,
        canShare = canShare,
        onShareClick = onShareClick,
        state = state.dailyVibe
      )
    }
  }
  val weatherSnapshot = state.weatherSnapshot
  item(key = RATING_CARD) {
    RatingCardHost(
      modifier = horizontalPadding,
      weatherSnapshot = weatherSnapshot,
      onNavigateToHistory = onNavigateToVibeHistory
    )
  }
  item(key = BRIEFING) {
    WeatherBriefingCard(
      modifier = horizontalPadding,
      onMusicClick = onMusicClick,
      onRetryClick = onRetrySuggestion,
      state = state.aiSuggestion.briefing
    )
  }
  item(key = HOURLY) {
    HourlyForecastRow(state = state.forecast.hourlyForecast)
  }
  item(key = ACTIVITY_PLANNER) {
    ActivityPlannerTeaserCard(
      modifier = horizontalPadding,
      onClick = onNavigateToActivityPlanner
    )
  }
  item(key = DAILY) {
    DailyForecastList(
      modifier = horizontalPadding,
      state = state.forecast.dailyForecast
    )
  }
  item(key = DETAILS) {
    DetailsPreviewCard(
      modifier = horizontalPadding,
      previewItems = state.details.previewItems,
      onClick = onNavigateToDetails
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ForecastList(
      state = Loaded(
        aiSuggestion = aiSuggestionSection,
        alert = smogAlert,
        dailyVibe = pleasantDailyVibeCard,
        details = detailsSections,
        forecast = forecastSection
      ),
      onNavigateToActivityPlanner = {},
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {},
      onNavigateToVibeHistory = {},
      onRefresh = {},
      onRetrySuggestion = {},
      onMusicClick = {},
      onShareClick = {}
    )
  }
}
