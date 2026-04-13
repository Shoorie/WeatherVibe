package com.weather.vibe.feature.search.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
  onLocationSelected: (Location) -> Unit,
  onNavigateBack: () -> Unit
) {

  val viewModel: SearchViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val keyboardController = LocalSoftwareKeyboardController.current

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      keyboardController?.hide()
      when (event) {
        is NavigateBack -> onNavigateBack()
        is NavigateBackWithResult -> onLocationSelected(event.location)
      }
    }
  }

  SearchContent(
    state = state,
    dispatch = viewModel::dispatch
  )
}
