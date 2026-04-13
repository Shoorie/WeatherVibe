package com.weather.vibe.feature.settings.ui.component.temperature

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.ui.SettingsResources.Emojis
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.temperatureSection
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.temperatureSectionSubtitle
import com.weather.vibe.feature.settings.ui.component.SettingsSection

@Composable
internal fun TemperatureSection(
  modifier: Modifier = Modifier,
  isCelsius: Boolean,
  onToggle: () -> Unit
) {
  SettingsSection(
    modifier = modifier,
    emoji = Emojis.temperature(),
    title = temperatureSection(),
    subtitle = temperatureSectionSubtitle()
  ) {
    TemperatureSegmentedControl(
      isCelsius = isCelsius,
      onSelectCelsius = { if (!isCelsius) onToggle() },
      onSelectFahrenheit = { if (isCelsius) onToggle() }
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewCelsius() {
  WeatherVibeTheme {
    TemperatureSection(
      modifier = Modifier.fillMaxWidth(),
      isCelsius = true,
      onToggle = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewFahrenheit() {
  WeatherVibeTheme {
    TemperatureSection(
      modifier = Modifier.fillMaxWidth(),
      isCelsius = false,
      onToggle = {}
    )
  }
}
