package com.weather.vibe.feature.search.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.search.presentation.SearchAction.SetMode
import com.weather.vibe.feature.search.presentation.SearchEvent.LimitReached
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.SearchMode
import com.weather.vibe.feature.search.presentation.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
  onNavigateBack: () -> Unit,
  onLocationSelected: (Location) -> Unit = {},
  onFavoritesLimitReached: () -> Unit = {},
  mode: SearchMode = SearchMode.Picker
) {

  val viewModel: SearchViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val favoritesCount by viewModel.favoritesCount.collectAsStateWithLifecycle()
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
        is LimitReached -> onFavoritesLimitReached()
      }
    }
  }

  SearchContent(
    state = state,
    mode = mode,
    favoritesCount = favoritesCount,
    dispatch = viewModel::dispatch
  )
}
