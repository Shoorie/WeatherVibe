package com.weather.vibe.feature.settings.notifications.ui.screen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.topbar.VibeTopBar
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.permissions.openSystemNotificationSettings
import com.weather.vibe.core.permissions.rememberNotificationPermissionGranted
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionLost
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.NavigateBack
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.OpenSystemNotificationSettings
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsViewModel
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Error
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loaded
import com.weather.vibe.feature.settings.notifications.preview.NotificationsPreview
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Emojis
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.errorTitle
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.screenTitle
import com.weather.vibe.feature.settings.shared.ui.component.ErrorContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {

  val viewModel: NotificationsViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val permissionGranted by rememberNotificationPermissionGranted()
  val context = LocalContext.current

  val callbacks = rememberNotificationsCallbacks(
    dispatch = viewModel::dispatch,
    notificationPermissionGranted = permissionGranted
  )

  LaunchedEffect(permissionGranted) {
    if (!permissionGranted) {
      viewModel.dispatch(NotificationPermissionLost)
    }
  }

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        NavigateBack -> onNavigateBack()
        OpenSystemNotificationSettings -> context.openSystemNotificationSettings()
      }
    }
  }

  NotificationsContent(
    state = state,
    callbacks = callbacks,
    notificationPermissionGranted = permissionGranted
  )
}

@Composable
internal fun NotificationsContent(
  modifier: Modifier = Modifier,
  state: NotificationsUiState,
  callbacks: NotificationsCallbacks,
  notificationPermissionGranted: Boolean
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
        .background(colors.backgroundGradientEnd)
    ) {
      when (state) {
        is Loaded -> NotificationsLoadedContent(
          state = state,
          callbacks = callbacks,
          notificationPermissionGranted = notificationPermissionGranted
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
  @PreviewParameter(NotificationsPreview::class)
  state: NotificationsUiState
) {
  WeatherVibeTheme {
    NotificationsContent(
      state = state,
      callbacks = NotificationsCallbacks.Noop,
      notificationPermissionGranted = true
    )
  }
}
