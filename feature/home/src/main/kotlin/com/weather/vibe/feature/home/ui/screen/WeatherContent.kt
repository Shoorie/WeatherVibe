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
import com.weather.vibe.feature.home.preview.HomePreviewData.aiSuggestionSection
import com.weather.vibe.feature.home.preview.HomePreviewData.detailsSections
import com.weather.vibe.feature.home.preview.HomePreviewData.forecastSection
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
  onShareClick: () -> Unit,
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
    onMusicClick = sheetCallbacks.onShow,
    onShareClick = onShareClick
  )

  if (showMoodSheet) {
    MoodPlaylistSheet(
      onDismiss = sheetCallbacks.onDismiss,
      onGenreRemoveClick = onGenreRemoveClick,
      onOpenSpotify = sheetCallbacks.onOpenSpotify,
      onOpenYtMusic = sheetCallbacks.onOpenYtMusic,
      state = state.aiSuggestion.playlist
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
        aiSuggestion = aiSuggestionSection,
        details = detailsSections,
        forecast = forecastSection
      ),
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {},
      onRefresh = {},
      onRetrySuggestion = {},
      onShareClick = {},
      onGenreRemoveClick = {}
    )
  }
}
