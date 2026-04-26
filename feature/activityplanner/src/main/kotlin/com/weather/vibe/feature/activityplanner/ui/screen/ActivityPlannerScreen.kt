package com.weather.vibe.feature.activityplanner.ui.screen

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerEvent.NavigateBack
import com.weather.vibe.feature.activityplanner.presentation.ActivityPlannerViewModel
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Error
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loaded
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loading
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreview
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.screenSubtitle
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.screenTitle
import com.weather.vibe.feature.activityplanner.ui.screen.callbacks.ActivityPlannerCallbacks
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ActivityPlannerScreen(
  onNavigateBack: () -> Unit,
  selectedLocation: Location
) {

  val viewModel = koinViewModel<ActivityPlannerViewModel> { parametersOf(selectedLocation) }
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = remember(viewModel) { ActivityPlannerCallbacks(viewModel) }

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        is NavigateBack -> onNavigateBack()
      }
    }
  }

  ActivityPlannerContent(
    state = state,
    onActivitySelect = callbacks.onActivitySelect,
    onBackClick = callbacks.onBackClick,
    onRetryClick = callbacks.onRetryClick
  )
}

@Composable
internal fun ActivityPlannerContent(
  modifier: Modifier = Modifier,
  state: ActivityPlannerUiState,
  onActivitySelect: (ActivityType) -> Unit,
  onBackClick: () -> Unit,
  onRetryClick: () -> Unit
) {
  VibeScreenScaffold(
    modifier = modifier.background(rememberAppBackgroundBrush()),
    header = {
      VibeScreenHeader(
        title = screenTitle(),
        subtitle = screenSubtitle(),
        onBackClicked = onBackClick
      )
    }
  ) {
    ScreenBody(
      state = state,
      onActivitySelect = onActivitySelect,
      onRetryClick = onRetryClick
    )
  }
}

@Composable
private fun ScreenBody(
  modifier: Modifier = Modifier,
  state: ActivityPlannerUiState,
  onActivitySelect: (ActivityType) -> Unit,
  onRetryClick: () -> Unit
) {
  when (state) {
    is Loading -> ActivityPlannerLoadingState(modifier = modifier)
    is Loaded -> ActivityPlannerLoadedContent(
      modifier = modifier,
      state = state,
      onActivitySelect = onActivitySelect
    )
    is Error -> ActivityPlannerErrorState(
      modifier = modifier,
      message = state.message,
      onRetryClick = onRetryClick
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ActivityPlannerPreview::class)
  state: ActivityPlannerUiState
) {
  WeatherVibeTheme {
    ActivityPlannerContent(
      state = state,
      onActivitySelect = {},
      onBackClick = {},
      onRetryClick = {}
    )
  }
}
