package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.topbar.VibeTopBar
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.permissions.openSystemNotificationSettings
import com.weather.vibe.core.permissions.rememberNotificationPermissionGranted
import com.weather.vibe.feature.settings.presentation.SettingsAction.NotificationPermissionLost
import com.weather.vibe.feature.settings.presentation.SettingsEvent.NavigateBack
import com.weather.vibe.feature.settings.presentation.SettingsEvent.OpenSystemNotificationSettings
import com.weather.vibe.feature.settings.presentation.SettingsViewModel
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Error
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loaded
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState.Loading
import com.weather.vibe.feature.settings.preview.SettingsPreview
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.screenTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {

  val viewModel: SettingsViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val permissionGranted by rememberNotificationPermissionGranted()
  val context = LocalContext.current

  val callbacks = rememberSettingsCallbacks(
    dispatch = viewModel::dispatch,
    notificationPermissionGranted = permissionGranted
  )

  LaunchedEffect(permissionGranted) {
    if (!permissionGranted) viewModel.dispatch(NotificationPermissionLost)
  }

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        is NavigateBack -> onNavigateBack()
        is OpenSystemNotificationSettings -> context.openSystemNotificationSettings()
      }
    }
  }

  SettingsContent(
    state = state,
    callbacks = callbacks,
    notificationPermissionGranted = permissionGranted
  )
}

@Composable
internal fun SettingsContent(
  modifier: Modifier = Modifier,
  state: SettingsUiState,
  callbacks: SettingsCallbacks,
  notificationPermissionGranted: Boolean
) {
  Scaffold(
    modifier = modifier,
    containerColor = colors.backgroundGradientEnd,
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
        .background(colors.backgroundGradientEnd)
    ) {
      when (state) {
        is Loading -> SettingsLoadingState()
        is Error -> SettingsErrorState(message = state.message)
        is Loaded -> SettingsLoadedContent(
          state = state,
          callbacks = callbacks,
          notificationPermissionGranted = notificationPermissionGranted
        )
      }
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
      callbacks = SettingsCallbacks.Noop,
      notificationPermissionGranted = true
    )
  }
}
