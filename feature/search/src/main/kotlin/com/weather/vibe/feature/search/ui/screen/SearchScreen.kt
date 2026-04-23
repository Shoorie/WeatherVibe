package com.weather.vibe.feature.search.ui.screen

import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.search.presentation.SearchAction.SetMode
import com.weather.vibe.feature.search.presentation.SearchEvent.LimitReached
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.SearchMode
import com.weather.vibe.feature.search.presentation.SearchViewModel
import com.weather.vibe.feature.search.ui.SearchResources
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SearchScreen(
  onNavigateBack: () -> Unit,
  onLocationSelected: (Location) -> Unit = {},
  mode: SearchMode = SearchMode.Picker
) {

  val viewModel: SearchViewModel = koinViewModel()
  val resources = koinInject<SearchResources>()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val favoritesCount by viewModel.favoritesCount.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }
  val keyboardController = LocalSoftwareKeyboardController.current

  LaunchedEffect(mode) {
    viewModel.dispatch(SetMode(mode))
  }

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        is NavigateBack -> {
          keyboardController?.hide()
          onNavigateBack()
        }
        is NavigateBackWithResult -> {
          keyboardController?.hide()
          onLocationSelected(event.location)
        }
        is LimitReached -> {
          snackbarHostState.currentSnackbarData?.dismiss()
          snackbarHostState.showSnackbar(
            message = resources.favoritesLimitReached(limit = MAX_FAVORITES),
            duration = Short
          )
        }
      }
    }
  }

  SearchContent(
    state = state,
    mode = mode,
    favoritesCount = favoritesCount,
    snackbarHostState = snackbarHostState,
    dispatch = viewModel::dispatch
  )
}
