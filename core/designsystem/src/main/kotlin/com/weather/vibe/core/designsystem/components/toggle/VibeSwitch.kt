package com.weather.vibe.core.designsystem.components.toggle

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
fun VibeSwitch(
  modifier: Modifier = Modifier,
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)? = null
) {
  Switch(
    modifier = modifier.minimumInteractiveComponentSize(),
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors = SwitchDefaults.colors(
      checkedThumbColor = colors.onAccent,
      checkedTrackColor = colors.accent,
      checkedBorderColor = colors.accent,
      uncheckedThumbColor = colors.onBackground,
      uncheckedTrackColor = colors.glassSurfaceHeavy,
      uncheckedBorderColor = colors.outline
    )
  )
}

@PreviewLightDark
@Composable
private fun PreviewOff() {
  WeatherVibeTheme {
    VibeSwitch(checked = false)
  }
}

@PreviewLightDark
@Composable
private fun PreviewOn() {
  WeatherVibeTheme {
    VibeSwitch(checked = true)
  }
}
