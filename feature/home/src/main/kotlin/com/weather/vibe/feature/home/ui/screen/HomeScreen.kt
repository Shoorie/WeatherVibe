package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.home.presentation.HomeAction.Initialize
import com.weather.vibe.feature.home.presentation.HomeAction.PosterCaptured
import com.weather.vibe.feature.home.presentation.HomeEvent.SharePoster
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.component.share.PosterCaptureHost
import com.weather.vibe.feature.home.ui.component.widgetpromo.WidgetPromoHost
import com.weather.vibe.feature.home.ui.screen.callbacks.HomeCallbacks
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
  onNavigateToActivityPlanner: () -> Unit = {},
  onNavigateToDetails: () -> Unit = {},
  onNavigateToSearch: () -> Unit = {},
  onNavigateToSettings: () -> Unit = {},
  onNavigateToVibeHistory: () -> Unit = {},
  onContentReady: () -> Unit = {},
  onPinWidget: () -> Unit = {},
  isWidgetAlreadyPinned: () -> Boolean = { false },
  pinWidgetSupported: Boolean = false,
  selectedLocation: Location
) {

  val viewModel = koinViewModel<HomeViewModel>()
  val state by viewModel.state.collectAsStateWithLifecycle()
  var pendingPoster by remember { mutableStateOf<SharePosterUiState?>(null) }
  val callbacks = remember(viewModel) { HomeCallbacks(viewModel) }

  LaunchedEffect(selectedLocation) {
    viewModel.dispatch(Initialize(selectedLocation))
  }

  LaunchedEffect(state) {
    if (state !is Loading) {
      onContentReady()
    }
  }

  LaunchedEffect(viewModel) {
    viewModel.event.collect { event ->
      when (event) {
        is SharePoster -> pendingPoster = event.state
      }
    }
  }

  HomeContent(
    state = state,
    onNavigateToActivityPlanner = onNavigateToActivityPlanner,
    onNavigateToDetails = onNavigateToDetails,
    onNavigateToSearch = onNavigateToSearch,
    onNavigateToSettings = onNavigateToSettings,
    onNavigateToVibeHistory = onNavigateToVibeHistory,
    onRefresh = callbacks.onRefresh,
    onRetrySuggestion = callbacks.onRetrySuggestion,
    onShareClick = callbacks.onShareClick,
    onGenreRemoveClick = callbacks.onGenreRemoveClick
  )

  pendingPoster?.let { poster ->
    PosterCaptureHost(state = poster) { bitmap ->
      viewModel.dispatch(PosterCaptured(bitmap))
      pendingPoster = null
    }
  }

  WidgetPromoHost(
    isHomeContentReady = state is Loaded,
    pinWidgetSupported = pinWidgetSupported,
    isWidgetAlreadyPinned = isWidgetAlreadyPinned,
    onPinWidget = onPinWidget
  )
}

@Composable
internal fun HomeContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  onNavigateToActivityPlanner: () -> Unit,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onNavigateToVibeHistory: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onShareClick: () -> Unit,
  onGenreRemoveClick: (String) -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(colors.screenSurface)
  ) {
    when (state) {
      is Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
      is Error -> HomeErrorContent(
        error = state.message,
        onRetry = onRefresh
      )
      is Loaded -> WeatherContent(
        state = state,
        onNavigateToActivityPlanner = onNavigateToActivityPlanner,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToVibeHistory = onNavigateToVibeHistory,
        onRefresh = onRefresh,
        onRetrySuggestion = onRetrySuggestion,
        onShareClick = onShareClick,
        onGenreRemoveClick = onGenreRemoveClick
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HomePreview::class)
  state: HomeUiState
) {
  WeatherVibeTheme {
    HomeContent(
      state = state,
      onNavigateToActivityPlanner = {},
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {},
      onNavigateToVibeHistory = {},
      onRefresh = {},
      onRetrySuggestion = {},
      onShareClick = {},
      onGenreRemoveClick = {}
    )
  }
}
