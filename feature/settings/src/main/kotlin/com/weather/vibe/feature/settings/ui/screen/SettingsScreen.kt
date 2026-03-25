package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.presentation.SettingsAction
import com.weather.vibe.feature.settings.presentation.SettingsEvent.NavigateBack
import com.weather.vibe.feature.settings.presentation.SettingsViewModel
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Error
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
  onNavigateBack: () -> Unit
) {
  val viewModel: SettingsViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        is NavigateBack -> onNavigateBack()
      }
    }
  }

  SettingsContent(
    state = state,
    dispatch = viewModel::dispatch
  )
}

@Composable
internal fun SettingsContent(
  modifier: Modifier = Modifier,
  state: SettingsUiState,
  dispatch: (SettingsAction) -> Unit
) {
  when (state) {
    is Loading -> SettingsLoadingState(modifier)
    is Loaded -> SettingsLoadedContent(modifier = modifier, state = state, dispatch = dispatch)
    is Error -> SettingsErrorState(modifier = modifier, message = state.message)
  }
}

@Composable
private fun SettingsLoadedContent(
  modifier: Modifier = Modifier,
  state: SettingsUiState.Loaded,
  dispatch: (SettingsAction) -> Unit
) {
  // TODO: implement Settings loaded UI — state.items available
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsContent(
      state = Loading,
      dispatch = {}
    )
  }
}

