package com.weather.vibe.feature.settings.ui.component.alerts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.ui.SettingsResources.Emojis
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.alertsSection
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.alertsSectionSubtitle
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.toggleOff
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.toggleOn
import com.weather.vibe.feature.settings.ui.component.SettingsSection
import com.weather.vibe.feature.settings.ui.component.SettingsToggle

@Composable
internal fun AlertsSection(
  modifier: Modifier = Modifier,
  enabled: Boolean,
  onToggle: (Boolean) -> Unit
) {
  SettingsSection(
    modifier = modifier,
    emoji = Emojis.alerts(),
    title = alertsSection(),
    subtitle = alertsSectionSubtitle(),
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
    AlertsSection(
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
    AlertsSection(
      modifier = Modifier.fillMaxWidth(),
      enabled = false,
      onToggle = {}
    )
  }
}
