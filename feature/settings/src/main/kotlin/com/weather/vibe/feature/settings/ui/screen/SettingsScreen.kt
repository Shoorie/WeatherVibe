package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.presentation.SettingsAction
import com.weather.vibe.feature.settings.presentation.SettingsAction.BackClick
import com.weather.vibe.feature.settings.presentation.SettingsAction.ExcludedGenresChange
import com.weather.vibe.feature.settings.presentation.SettingsAction.PersonaSelect
import com.weather.vibe.feature.settings.presentation.SettingsAction.TemperatureUnitToggle
import com.weather.vibe.feature.settings.presentation.SettingsEvent.NavigateBack
import com.weather.vibe.feature.settings.presentation.SettingsViewModel
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Error
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import com.weather.vibe.feature.settings.preview.SettingsPreview
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  dispatch: (SettingsAction) -> Unit
) {
  Scaffold(
    modifier = modifier,
    containerColor = colors.backgroundGradientStart,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = Texts.screenTitle(),
            color = colors.onBackground,
            style = typography.titleLarge
          )
        },
        navigationIcon = {
          IconButton(onClick = { dispatch(BackClick) }) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
              tint = colors.onBackground
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = colors.backgroundGradientStart
        )
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(innerPadding)
        .imePadding()
        .verticalScroll(rememberScrollState())
        .padding(PaddingMedium)
    ) {
      SettingsPersonaSection(
        onPersonaSelect = { dispatch(PersonaSelect(persona = it)) },
        personaOptions = state.personaOptions
      )
      SettingsTemperatureSection(
        isCelsius = state.isCelsius,
        onToggle = { dispatch(TemperatureUnitToggle) },
        modifier = Modifier.padding(top = PaddingLarge)
      )
      SettingsMusicSection(
        excludedGenres = state.excludedGenres,
        onExcludedGenresChange = { dispatch(ExcludedGenresChange(genres = it)) },
        modifier = Modifier.padding(top = PaddingLarge)
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SettingsPreview::class)
  state: SettingsUiState
) {
  WeatherVibeTheme {
    SettingsContent(
      state = state,
      dispatch = {}
    )
  }
}
