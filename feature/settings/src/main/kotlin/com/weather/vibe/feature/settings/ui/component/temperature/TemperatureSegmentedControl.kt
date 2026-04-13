package com.weather.vibe.feature.settings.ui.component.temperature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.settings.ui.SettingsDefaults.SegmentControlMinHeight
import com.weather.vibe.feature.settings.ui.SettingsDefaults.SegmentControlPadding
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.celsiusLabel
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.fahrenheitLabel
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.segmentBackground
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.segmentLabelColor
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.segmentLabelStyle

@Composable
internal fun TemperatureSegmentedControl(
  modifier: Modifier = Modifier,
  isCelsius: Boolean,
  onSelectCelsius: () -> Unit,
  onSelectFahrenheit: () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.pill)
      .background(colors.surfaceVariant)
      .border(Border, colors.outline, shapes.pill)
      .padding(SegmentControlPadding)
  ) {
    Segment(
      modifier = Modifier.weight(1f),
      label = celsiusLabel(),
      isSelected = isCelsius,
      onClick = onSelectCelsius
    )
    Segment(
      modifier = Modifier.weight(1f),
      label = fahrenheitLabel(),
      isSelected = !isCelsius,
      onClick = onSelectFahrenheit
    )
  }
}

@Composable
private fun Segment(
  modifier: Modifier = Modifier,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .clip(shapes.pill)
      .background(segmentBackground(isSelected))
      .selectable(
        selected = isSelected,
        role = Role.Tab,
        onClick = onClick
      )
      .defaultMinSize(minHeight = SegmentControlMinHeight)
      .padding(horizontal = Medium)
      .padding(vertical = Small),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = label,
      style = segmentLabelStyle(isSelected),
      color = segmentLabelColor(isSelected)
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewCelsius() {
  WeatherVibeTheme {
    TemperatureSegmentedControl(
      isCelsius = true,
      onSelectCelsius = {},
      onSelectFahrenheit = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewFahrenheit() {
  WeatherVibeTheme {
    TemperatureSegmentedControl(
      isCelsius = false,
      onSelectCelsius = {},
      onSelectFahrenheit = {}
    )
  }
}
