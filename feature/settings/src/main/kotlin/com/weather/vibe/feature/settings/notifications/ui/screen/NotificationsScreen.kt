package com.weather.vibe.feature.settings.notifications.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.core.permissions.notification.openSystemNotificationSettings
import com.weather.vibe.core.permissions.notification.rememberNotificationPermissionGranted
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsAction.NotificationPermissionLost
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.NavigateBack
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.OpenSystemNotificationSettings
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsEvent.ShowSettingsSaveError
import com.weather.vibe.feature.settings.notifications.presentation.NotificationsViewModel
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Error
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loaded
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loading
import com.weather.vibe.feature.settings.notifications.preview.NotificationsPreview
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Emojis
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.errorTitle
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.screenSubtitle
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.screenTitle
import com.weather.vibe.feature.settings.shared.ui.component.ErrorContent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {

  val viewModel: NotificationsViewModel = koinViewModel()
  val resources: NotificationsResources = koinInject()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val permissionGranted by rememberNotificationPermissionGranted()
  val context = LocalContext.current
  val snackbarHostState = remember { SnackbarHostState() }

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
        ShowSettingsSaveError -> snackbarHostState.showSnackbar(message = resources.defaultError())
      }
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    NotificationsContent(
      state = state,
      callbacks = callbacks,
      notificationPermissionGranted = permissionGranted
    )
    SnackbarHost(
      hostState = snackbarHostState,
      modifier = Modifier.align(Alignment.BottomCenter)
    )
  }
}

@Composable
internal fun NotificationsContent(
  modifier: Modifier = Modifier,
  state: NotificationsUiState,
  callbacks: NotificationsCallbacks,
  notificationPermissionGranted: Boolean
) {
  VibeScreenScaffold(
    modifier = modifier.background(rememberAppBackgroundBrush()),
    header = {
      VibeScreenHeader(
        title = screenTitle(),
        subtitle = screenSubtitle(),
        onBackClicked = callbacks.onBackClick
      )
    }
  ) {
    when (state) {
      is Loading -> NotificationsLoadingContent(modifier = Modifier.fillMaxSize())
      is Loaded -> NotificationsLoadedContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        callbacks = callbacks,
        notificationPermissionGranted = notificationPermissionGranted
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

@Composable
private fun NotificationsLoadingContent(modifier: Modifier = Modifier) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
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
