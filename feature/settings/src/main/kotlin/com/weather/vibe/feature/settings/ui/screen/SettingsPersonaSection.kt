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
import com.weather.vibe.core.designsystem.theme.AppDimens.BorderThickness
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.settings.model.Persona
import com.weather.vibe.feature.settings.presentation.state.PersonaOptionUiState
import com.weather.vibe.feature.settings.preview.SettingsPreviewData
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.personaOptions
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts

@Composable
internal fun SettingsPersonaSection(
  modifier: Modifier = Modifier,
  onPersonaSelect: (Persona) -> Unit,
  personaOptions: List<PersonaOptionUiState>
) {
  Column(modifier = modifier) {
    Text(
      text = Texts.aiPersonaSection(),
      color = colors.onSurfaceVariant,
      style = typography.labelMedium,
      modifier = Modifier.padding(bottom = PaddingSmall)
    )
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .border(
          width = BorderThickness,
          color = colors.glassBorder,
          shape = shapes.cardSmall
        )
    ) {
      personaOptions.forEach { option ->
        PersonaRow(
          option = option,
          onSelect = { onPersonaSelect(option.persona) }
        )
      }
    }
  }
}

@Composable
private fun PersonaRow(
  option: PersonaOptionUiState,
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
    Text(
      modifier = Modifier.padding(start = PaddingSmall),
      text = option.label,
      color = colors.onBackground,
      style = typography.bodyMedium
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsPersonaSection(
      onPersonaSelect = {},
      personaOptions = personaOptions
    )
  }
}
