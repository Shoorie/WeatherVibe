package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.home.presentation.HomeAction.Initialize
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.screen.callbacks.HomeCallbacks
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
  onNavigateToDetails: () -> Unit = {},
  onNavigateToSearch: () -> Unit = {},
  onNavigateToSettings: () -> Unit = {},
  selectedLocation: Location? = null
) {

  val viewModel = koinViewModel<HomeViewModel>()
  val state by viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(selectedLocation) {
    viewModel.dispatch(Initialize(selectedLocation))
  }

  val callbacks = remember(viewModel) { HomeCallbacks(viewModel) }

  HomeContent(
    state = state,
    onNavigateToDetails = onNavigateToDetails,
    onNavigateToSearch = onNavigateToSearch,
    onNavigateToSettings = onNavigateToSettings,
    onRefresh = callbacks.onRefresh,
    onRetrySuggestion = callbacks.onRetrySuggestion,
    onGenreRemoveClick = callbacks.onGenreRemoveClick
  )
}

@Composable
internal fun HomeContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onGenreRemoveClick: (String) -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(color = colors.backgroundGradientEnd)
  ) {
    when (state) {
      is Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
      is Error -> HomeErrorContent(
        error = state.message,
        onRetry = onRefresh
      )
      is Loaded -> WeatherContent(
        state = state,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToSettings = onNavigateToSettings,
        onRefresh = onRefresh,
        onRetrySuggestion = onRetrySuggestion,
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
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {},
      onRefresh = {},
      onRetrySuggestion = {},
      onGenreRemoveClick = {}
    )
  }
}
