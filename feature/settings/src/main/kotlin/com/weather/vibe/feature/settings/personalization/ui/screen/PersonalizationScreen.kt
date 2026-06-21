package com.weather.vibe.feature.settings.personalization.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.NavigateBack
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationEvent.ShowPremiumUnavailable
import com.weather.vibe.feature.settings.personalization.presentation.PersonalizationViewModel
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Error
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loaded
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonalizationUiState.Loading
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreview
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Emojis
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.errorTitle
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.premiumComingSoon
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.screenSubtitle
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.screenTitle
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.PaywallSheet
import com.weather.vibe.feature.settings.shared.ui.component.ErrorContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonalizationScreen(onNavigateBack: () -> Unit) {

  val viewModel: PersonalizationViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = rememberPersonalizationCallbacks(dispatch = viewModel::dispatch)
  val snackbarHostState = remember { SnackbarHostState() }
  val premiumMessage = premiumComingSoon()

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        NavigateBack -> onNavigateBack()
        ShowPremiumUnavailable -> snackbarHostState.showSnackbar(premiumMessage)
      }
    }
  }

  PersonalizationContent(
    state = state,
    callbacks = callbacks,
    snackbarHostState = snackbarHostState
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PersonalizationContent(
  modifier: Modifier = Modifier,
  state: PersonalizationUiState,
  callbacks: PersonalizationCallbacks,
  snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
  val backgroundTarget = when (state) {
    is Loaded -> PersonaPalette.colorsFor(state.narrator.colorKey).soft
    else -> colors.screenSurface
  }
  val background by animateColorAsState(targetValue = backgroundTarget, label = "personaBackground")

  Box(modifier = modifier) {
    VibeScreenScaffold(
      modifier = Modifier.background(background),
      header = {
        VibeScreenHeader(
          title = screenTitle(),
          subtitle = screenSubtitle(),
          onBackClicked = callbacks.onBackClick
        )
      }
    ) {
      when (state) {
        is Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
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

    SnackbarHost(
      hostState = snackbarHostState,
      modifier = Modifier.align(Alignment.BottomCenter)
    )
  }

  val paywall = (state as? Loaded)?.paywall
  if (paywall != null) {
    ModalBottomSheet(
      onDismissRequest = callbacks.onPaywallDismiss,
      sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
      containerColor = colors.sheetSurface
    ) {
      PaywallSheet(
        onBuyPremium = callbacks.onBuyPremium,
        onDismiss = callbacks.onPaywallDismiss,
        onUnlockedViaAd = { callbacks.onToneUnlockedViaAd(paywall.tone) },
        paywall = paywall
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
