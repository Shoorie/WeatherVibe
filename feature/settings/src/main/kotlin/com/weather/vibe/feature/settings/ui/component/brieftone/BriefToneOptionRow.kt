package com.weather.vibe.feature.settings.ui.component.brieftone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.briefToneOptions
import com.weather.vibe.feature.settings.ui.SettingsDefaults.BriefToneRowMinHeight
import com.weather.vibe.feature.settings.ui.SettingsDefaults.SelectedIndicatorSize
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.briefToneSelectedContentDescription
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.briefToneDescriptionColor
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.briefToneLabelColor
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.briefToneLabelStyle
import com.weather.vibe.feature.settings.ui.SettingsTextStyles.briefToneRowBackground

@Composable
internal fun BriefToneOptionRow(
  modifier: Modifier = Modifier,
  option: BriefToneOptionUiState,
  onSelect: () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.cardSmall)
      .background(briefToneRowBackground(option.isSelected))
      .selectable(
        selected = option.isSelected,
        role = Role.RadioButton,
        onClick = onSelect
      )
      .defaultMinSize(minHeight = BriefToneRowMinHeight)
      .padding(horizontal = Medium)
      .padding(vertical = Small),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = option.label,
        style = briefToneLabelStyle(option.isSelected),
        color = briefToneLabelColor(option.isSelected)
      )
      Text(
        text = option.description,
        style = typography.bodySmall,
        color = briefToneDescriptionColor(option.isSelected)
      )
    }
    if (option.isSelected) {
      Icon(
        modifier = Modifier.size(SelectedIndicatorSize),
        imageVector = Icons.Default.Check,
        contentDescription = briefToneSelectedContentDescription(),
        tint = colors.accent
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun PreviewSelected() {
  WeatherVibeTheme {
    BriefToneOptionRow(
      option = briefToneOptions.first { it.isSelected },
      onSelect = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewUnselected() {
  WeatherVibeTheme {
    BriefToneOptionRow(
      option = briefToneOptions.first { !it.isSelected },
      onSelect = {}
    )
  }
}
