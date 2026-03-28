package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts

@Composable
internal fun SettingsTemperatureSection(
  modifier: Modifier = Modifier,
  isCelsius: Boolean,
  onToggle: () -> Unit
) {
  Column(modifier = modifier) {
    Text(
      text = Texts.temperatureSection(),
      color = colors.onSurfaceVariant,
      style = typography.labelMedium,
      modifier = Modifier.padding(bottom = PaddingSmall)
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (isCelsius) Texts.celsiusLabel() else Texts.fahrenheitLabel(),
        color = colors.onBackground,
        style = typography.bodyMedium,
        modifier = Modifier.weight(weight = 1f)
      )
      Switch(
        checked = isCelsius,
        onCheckedChange = { onToggle() },
        colors = SwitchDefaults.colors(
          checkedBorderColor = colors.accent,
          checkedThumbColor = colors.onBackground,
          checkedTrackColor = colors.accent,
          uncheckedBorderColor = colors.glassBorder,
          uncheckedThumbColor = colors.onSurfaceVariant,
          uncheckedTrackColor = colors.glassBorder
        )
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsTemperatureSection(
      isCelsius = true,
      onToggle = {}
    )
  }
}
