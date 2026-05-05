package com.weather.vibe.feature.settings.notifications.ui.component.pollenalerts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Emojis
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.pollenAlertsSection
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.pollenAlertsSectionSubtitle
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.toggleOff
import com.weather.vibe.feature.settings.notifications.ui.NotificationsResources.Texts.toggleOn
import com.weather.vibe.feature.settings.shared.ui.component.SettingsSection
import com.weather.vibe.feature.settings.shared.ui.component.SettingsToggle

@Composable
internal fun PollenAlertsSection(
  modifier: Modifier = Modifier,
  enabled: Boolean,
  onToggle: (Boolean) -> Unit
) {
  SettingsSection(
    modifier = modifier,
    emoji = Emojis.pollenAlerts(),
    title = pollenAlertsSection(),
    subtitle = pollenAlertsSectionSubtitle(),
    toggle = SettingsToggle(
      checked = enabled,
      onChange = onToggle,
      stateLabel = if (enabled) toggleOn() else toggleOff()
    )
  )
}

@PreviewLightDark
@Composable
private fun PreviewEnabled() {
  WeatherVibeTheme {
    PollenAlertsSection(
      modifier = Modifier.fillMaxWidth(),
      enabled = true,
      onToggle = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewDisabled() {
  WeatherVibeTheme {
    PollenAlertsSection(
      modifier = Modifier.fillMaxWidth(),
      enabled = false,
      onToggle = {}
    )
  }
}
