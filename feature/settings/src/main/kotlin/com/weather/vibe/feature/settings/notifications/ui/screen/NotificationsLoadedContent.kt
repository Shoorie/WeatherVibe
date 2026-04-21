package com.weather.vibe.feature.settings.notifications.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState.Loaded
import com.weather.vibe.feature.settings.notifications.ui.NotificationsKeys.KEY_ALERTS
import com.weather.vibe.feature.settings.notifications.ui.NotificationsKeys.KEY_MORNING_BRIEF
import com.weather.vibe.feature.settings.notifications.ui.component.alerts.AlertsSection
import com.weather.vibe.feature.settings.notifications.ui.component.morningbrief.MorningBriefSection

@Composable
internal fun NotificationsLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  callbacks: NotificationsCallbacks,
  notificationPermissionGranted: Boolean
) {

  val contentPadding = remember {
    PaddingValues(
      start = Medium,
      end = Medium,
      top = Medium,
      bottom = ExtraLarge
    )
  }

  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    item(key = KEY_MORNING_BRIEF) {
      MorningBriefSection(
        enabled = state.morningBriefEnabled && notificationPermissionGranted,
        onToggle = callbacks.onMorningBriefToggle
      )
    }
    item(key = KEY_ALERTS) {
      AlertsSection(
        enabled = state.alertsEnabled && notificationPermissionGranted,
        onToggle = callbacks.onAlertsToggle
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    NotificationsLoadedContent(
      state = Loaded(
        alertsEnabled = true,
        morningBriefEnabled = true
      ),
      callbacks = NotificationsCallbacks.Noop,
      notificationPermissionGranted = true
    )
  }
}
