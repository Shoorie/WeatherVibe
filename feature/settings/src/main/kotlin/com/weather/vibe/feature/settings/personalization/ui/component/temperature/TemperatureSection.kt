package com.weather.vibe.feature.settings.personalization.ui.component.temperature

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.segmented.VibeSegment
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedControl
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Emojis
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.celsiusLabel
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.fahrenheitLabel
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.temperatureSection
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.temperatureSectionSubtitle
import com.weather.vibe.feature.settings.shared.ui.component.SettingsSection
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun TemperatureSection(
  modifier: Modifier = Modifier,
  isCelsius: Boolean,
  onToggle: () -> Unit
) {

  val celsius = celsiusLabel()
  val fahrenheit = fahrenheitLabel()
  val segments = persistentListOf(
    VibeSegment(
      value = true,
      label = celsius,
      contentDescription = celsius,
      isSelected = isCelsius
    ),
    VibeSegment(
      value = false,
      label = fahrenheit,
      contentDescription = fahrenheit,
      isSelected = !isCelsius
    )
  )

  SettingsSection(
    modifier = modifier,
    emoji = Emojis.temperature(),
    title = temperatureSection(),
    subtitle = temperatureSectionSubtitle()
  ) {
    VibeSegmentedControl(
      segments = segments,
      onSegmentClick = { isCelsiusSelected -> if (isCelsiusSelected != isCelsius) onToggle() }
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
