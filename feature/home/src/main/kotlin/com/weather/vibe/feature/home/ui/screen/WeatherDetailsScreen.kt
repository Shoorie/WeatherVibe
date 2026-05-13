package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.ads.ui.AdSlot
import com.weather.vibe.core.ads.ui.adSlotBottomInset
import com.weather.vibe.core.ads.ui.rememberAdSlotState
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.domain.ads.placement.AdPlacement.WeatherDetailsBottom
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.home.presentation.DetailsViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.HomeForecastTexts.weatherDetailsSubtitle
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
  VibeScreenScaffold(
    modifier = modifier.background(rememberAppBackgroundBrush()),
    header = {
      VibeScreenHeader(
        title = weatherDetailsTitle(),
        subtitle = weatherDetailsSubtitle(),
        onBackClicked = onNavigateBack
      )
    }
  ) {
    val adSlotState = rememberAdSlotState(WeatherDetailsBottom)
    val adBottomInset = adSlotBottomInset(adSlotState)
    Box(modifier = Modifier.fillMaxSize()) {
      val contentModifier = Modifier
        .fillMaxSize()
        .padding(bottom = adBottomInset)
      when (state) {
        is Loading -> LoadingIndicator(modifier = contentModifier)
        is Error -> HomeErrorContent(
          modifier = contentModifier,
          error = state.message,
          onRetry = onNavigateBack
        )
        is Loaded -> WeatherDetailsLoadedContent(
          modifier = contentModifier,
          state = state
        )
      }
      AdSlot(
        modifier = Modifier.align(Alignment.BottomCenter),
        state = adSlotState
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
