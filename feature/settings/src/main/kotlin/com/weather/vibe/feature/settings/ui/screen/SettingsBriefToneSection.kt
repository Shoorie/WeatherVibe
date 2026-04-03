package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.AppDimens.BorderThickness
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.briefToneOptions
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts

@Composable
internal fun SettingsBriefToneSection(
  modifier: Modifier = Modifier,
  briefToneOptions: List<BriefToneOptionUiState>,
  onBriefToneSelect: (BriefTone) -> Unit
) {
  SectionLabel(
    modifier = modifier,
    text = Texts.briefToneSection()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .border(
          width = BorderThickness,
          color = colors.glassBorder,
          shape = shapes.cardSmall
        )
    ) {
      briefToneOptions.forEach { option ->
        BriefToneRow(
          option = option,
          onSelect = { onBriefToneSelect(option.tone) }
        )
      }
    }
  }
}

@Composable
private fun BriefToneRow(
  option: BriefToneOptionUiState,
  onSelect: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .padding(horizontal = PaddingMedium, vertical = PaddingSmall),
    verticalAlignment = Alignment.CenterVertically
  ) {
    RadioButton(
      selected = option.isSelected,
      onClick = onSelect,
      colors = RadioButtonDefaults.colors(selectedColor = colors.accent)
    )
    Column(modifier = Modifier.padding(start = PaddingSmall)) {
      Text(
        text = option.label,
        color = colors.onBackground,
        style = typography.bodyMedium
      )
      Text(
        text = option.description,
        color = colors.onSurfaceVariant,
        style = typography.bodySmall
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsBriefToneSection(
      briefToneOptions = briefToneOptions,
      onBriefToneSelect = {}
    )
  }
}
