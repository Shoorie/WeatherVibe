package com.weather.vibe.feature.settings.ui.component.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.chip.VibeInputChip
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.presentation.state.GenreChipSettingsUiState
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.genreChips
import com.weather.vibe.feature.settings.ui.SettingsResources.Emojis
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.excludedGenresSection
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.excludedGenresSectionSubtitle
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts.genreRemoveContentDescription
import com.weather.vibe.feature.settings.ui.component.SettingsSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ExcludedGenresSection(
  modifier: Modifier = Modifier,
  genreChips: List<GenreChipSettingsUiState>,
  onGenreRemove: (String) -> Unit
) {
  SettingsSection(
    modifier = modifier,
    emoji = Emojis.excludedGenres(),
    title = excludedGenresSection(),
    subtitle = excludedGenresSectionSubtitle()
  ) {
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(Small),
      verticalArrangement = Arrangement.spacedBy(Small)
    ) {
      genreChips.forEach { chip ->
        VibeInputChip(
          label = chip.name,
          selected = true,
          onDismiss = { onGenreRemove(chip.name) },
          dismissContentDescription = genreRemoveContentDescription(chip.name)
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ExcludedGenresSection(
      modifier = Modifier.fillMaxWidth(),
      genreChips = genreChips,
      onGenreRemove = {}
    )
  }
}
