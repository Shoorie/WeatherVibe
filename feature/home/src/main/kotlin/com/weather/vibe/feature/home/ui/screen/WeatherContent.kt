package com.weather.vibe.feature.home.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreviewData.afternoonSunInfo
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.eightHoursForecast
import com.weather.vibe.feature.home.preview.HomePreviewData.header
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist
import com.weather.vibe.feature.home.preview.HomePreviewData.warmDayCurrent
import com.weather.vibe.feature.home.preview.HomePreviewData.weekForecast
import com.weather.vibe.feature.home.ui.component.mood.MoodPlaylistSheet
import com.weather.vibe.feature.home.ui.screen.callbacks.MoodSheetCallbacks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeatherContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onGenreRemoveClick: (String) -> Unit
) {

  var showMoodSheet by rememberSaveable { mutableStateOf(value = false) }
  val uriHandler = LocalUriHandler.current
  val sheetCallbacks = remember(uriHandler) {
    MoodSheetCallbacks(uriHandler) { showMoodSheet = it }
  }

  ForecastList(
    modifier = modifier,
    state = state,
    onNavigateToDetails = onNavigateToDetails,
    onNavigateToSearch = onNavigateToSearch,
    onNavigateToSettings = onNavigateToSettings,
    onRefresh = onRefresh,
    onRetrySuggestion = onRetrySuggestion,
    onMusicClick = sheetCallbacks.onShow
  )

  if (showMoodSheet) {
    MoodPlaylistSheet(
      onDismiss = sheetCallbacks.onDismiss,
      onGenreRemoveClick = onGenreRemoveClick,
      onOpenSpotify = sheetCallbacks.onOpenSpotify,
      onOpenYtMusic = sheetCallbacks.onOpenYtMusic,
      state = state.playlist
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WeatherContent(
      state = Loaded(
        currentWeather = warmDayCurrent,
        dailyForecast = weekForecast,
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
      onGenreRemoveClick = {}
    )
  }
}
