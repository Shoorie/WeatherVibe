package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationViewModel
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreview
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Emojis
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.errorTitle
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.screenSubtitle
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.screenTitle
import com.weather.vibe.feature.settings.shared.ui.component.ErrorContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonalizationScreen(onNavigateBack: () -> Unit) {

  val viewModel: PersonalizationViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = rememberPersonalizationCallbacks(dispatch = viewModel::dispatch)

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        NavigateBack -> onNavigateBack()
      }
    }
  }

  PersonalizationContent(
    state = state,
    callbacks = callbacks
  )
}

@Composable
internal fun PersonalizationContent(
  modifier: Modifier = Modifier,
  state: PersonalizationUiState,
  callbacks: PersonalizationCallbacks
) {
  VibeScreenScaffold(
    modifier = modifier,
    header = {
      VibeScreenHeader(
        title = screenTitle(),
        subtitle = screenSubtitle(),
        onBackClicked = callbacks.onBackClick
      )
    }
  ) {
    when (state) {
      is Loaded -> PersonalizationLoadedContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        callbacks = callbacks
      )
      is Error -> ErrorContent(
        modifier = Modifier.fillMaxSize(),
        emoji = Emojis.error(),
        title = errorTitle(),
        message = state.message
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(PersonalizationPreview::class)
  state: PersonalizationUiState
) {
  WeatherVibeTheme {
    PersonalizationContent(
      state = state,
      callbacks = PersonalizationCallbacks.Noop
    )
  }
}
