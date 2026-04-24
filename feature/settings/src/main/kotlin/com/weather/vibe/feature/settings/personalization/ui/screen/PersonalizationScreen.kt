package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.topbar.VibeTopBar
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationViewModel
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreview
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Emojis
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.errorTitle
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
  Scaffold(
    modifier = modifier,
    containerColor = colors.backgroundGradientEnd,
    contentColor = Color.Unspecified,
    topBar = {
      VibeTopBar(
        title = screenTitle(),
        onNavigateBack = callbacks.onBackClick
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(rememberAppBackgroundBrush())
    ) {
      when (state) {
        is Loaded -> PersonalizationLoadedContent(
          state = state,
          callbacks = callbacks
        )
        is Error -> ErrorContent(
          emoji = Emojis.error(),
          title = errorTitle(),
          message = state.message
        )
      }
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
