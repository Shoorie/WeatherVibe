package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts

@Composable
internal fun SettingsMusicSection(
  modifier: Modifier = Modifier,
  excludedGenres: String,
  onExcludedGenresChange: (String) -> Unit
) {
  Column(modifier = modifier) {
    Text(
      modifier = Modifier.padding(bottom = PaddingSmall),
      text = Texts.excludedGenresSection(),
      color = colors.onSurfaceVariant,
      style = typography.labelMedium
    )
    OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = excludedGenres,
      onValueChange = onExcludedGenresChange,
      placeholder = {
        Text(
          text = Texts.excludedGenresHint(),
          color = colors.onSurfaceVariant,
          style = typography.bodyMedium
        )
      },
      textStyle = typography.bodyMedium,
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colors.accent,
        unfocusedBorderColor = colors.glassBorder,
        focusedTextColor = colors.onBackground,
        unfocusedTextColor = colors.onBackground
      )
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsMusicSection(
      excludedGenres = "jazz, classical",
      onExcludedGenresChange = {}
    )
  }
}
