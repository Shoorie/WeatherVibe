package com.weather.vibe.feature.locations.ui.screen

import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.locations.presentation.LocationsAction
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.presentation.LocationsAction.Initialize
import com.weather.vibe.feature.locations.presentation.LocationsAction.UndoRemoveLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsEvent
import com.weather.vibe.feature.locations.presentation.LocationsEvent.NavigateToSearch
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowLimitReachedSnackbar
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowRemovedSnackbar
import com.weather.vibe.feature.locations.presentation.LocationsViewModel
import com.weather.vibe.feature.locations.ui.LocationsResources
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LocationsScreen(
  modifier: Modifier = Modifier,
  onNavigateToSearch: () -> Unit = {}
) {
  val viewModel = koinViewModel<LocationsViewModel>()
  val resources = koinInject<LocationsResources>()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val dispatch = remember(viewModel) { viewModel::dispatch }
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(viewModel) {
    viewModel.dispatch(Initialize)
  }

  LaunchedEffect(viewModel) {
    viewModel.event.collect { event ->
      onEvent(
        event = event,
        resources = resources,
        host = snackbarHostState,
        dispatch = dispatch,
        onNavigateToSearch = onNavigateToSearch
      )
    }
  }

  LocationsContent(
    modifier = modifier,
    state = state,
    snackbarHostState = snackbarHostState,
    dispatch = dispatch
  )
}

private suspend fun onEvent(
  event: LocationsEvent,
  resources: LocationsResources,
  host: SnackbarHostState,
  dispatch: (LocationsAction) -> Unit,
  onNavigateToSearch: () -> Unit
) {
  when (event) {
    is NavigateToSearch -> onNavigateToSearch()
    is ShowRemovedSnackbar -> onRemovedSnackbar(
      event = event,
      resources = resources,
      snackbarHostState = host,
      dispatch = dispatch
    )
    is ShowLimitReachedSnackbar -> host.showSnackbar(
      message = resources.limitReached(MAX_FAVORITES),
      duration = Short
    )
  }
}

private suspend fun onRemovedSnackbar(
  event: ShowRemovedSnackbar,
  resources: LocationsResources,
  snackbarHostState: SnackbarHostState,
  dispatch: (LocationsAction) -> Unit
) {

  val result = snackbarHostState.showSnackbar(
    message = resources.removedSnackbar(name = event.locationName),
    actionLabel = resources.undoAction(),
    duration = Short
  )

  if (result == ActionPerformed) {
    dispatch(
      UndoRemoveLocationFavoriteClick(
        location = event.location,
        label = event.label
      )
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationsContent(
      state = LocationsPreviewData.browseLoaded,
      snackbarHostState = remember { SnackbarHostState() },
      dispatch = {}
    )
  }
}
