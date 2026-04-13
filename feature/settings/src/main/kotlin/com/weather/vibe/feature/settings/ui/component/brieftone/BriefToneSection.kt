package com.weather.vibe.feature.settings.ui.component.brieftone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.presentation.state.BriefToneOptionUiState
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.briefToneOptions
import com.weather.vibe.feature.settings.ui.SettingsResources.Emojis
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.briefToneSection
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.briefToneSectionSubtitle
import com.weather.vibe.feature.settings.ui.component.SettingsSection

@Composable
internal fun BriefToneSection(
  modifier: Modifier = Modifier,
  briefToneOptions: List<BriefToneOptionUiState>,
  onBriefToneSelect: (BriefTone) -> Unit
) {
  SettingsSection(
    modifier = modifier,
    emoji = Emojis.briefTone(),
    title = briefToneSection(),
    subtitle = briefToneSectionSubtitle()
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(ExtraSmall)) {
      briefToneOptions.forEach { option ->
        BriefToneOptionRow(
          option = option,
          onSelect = { onBriefToneSelect(option.tone) }
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefToneSection(
      modifier = Modifier.fillMaxWidth(),
      briefToneOptions = briefToneOptions,
      onBriefToneSelect = {}
    )
  }
}
