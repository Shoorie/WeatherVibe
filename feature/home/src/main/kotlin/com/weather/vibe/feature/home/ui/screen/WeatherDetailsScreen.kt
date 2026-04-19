package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.components.topbar.VibeTopBar
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.home.presentation.DetailsViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.HomeForecastTexts.weatherDetailsTitle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun WeatherDetailsScreen(
  onNavigateBack: () -> Unit = {},
  selectedLocation: Location
) {

  val viewModel = koinViewModel<DetailsViewModel> { parametersOf(selectedLocation) }
  val state by viewModel.state.collectAsStateWithLifecycle()

  WeatherDetailsContent(
    state = state,
    onNavigateBack = onNavigateBack
  )
}

@Composable
internal fun WeatherDetailsContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  onNavigateBack: () -> Unit
) {
  Scaffold(
    modifier = modifier,
    containerColor = colors.backgroundGradientStart,
    topBar = {
      VibeTopBar(
        title = weatherDetailsTitle(),
        onNavigateBack = onNavigateBack
      )
    }
  ) { innerPadding ->
    when (state) {
      is Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
      is Error -> HomeErrorContent(
        modifier = Modifier.padding(innerPadding),
        error = state.message,
        onRetry = onNavigateBack
      )
      is Loaded -> WeatherDetailsLoadedContent(
        modifier = Modifier.padding(innerPadding),
        state = state
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
    WeatherDetailsContent(
      state = state,
      onNavigateBack = {}
    )
  }
}
