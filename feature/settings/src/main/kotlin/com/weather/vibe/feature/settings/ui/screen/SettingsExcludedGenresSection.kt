package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.chip.VibeInputChip
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.settings.presentation.state.GenreChipSettingsUiState
import com.weather.vibe.feature.settings.preview.SettingsPreviewData.genreChips
import com.weather.vibe.feature.settings.ui.SettingsResources.Texts

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SettingsExcludedGenresSection(
  modifier: Modifier = Modifier,
  genreChips: List<GenreChipSettingsUiState>,
  onGenreRemove: (String) -> Unit
) {
  SectionLabel(
    modifier = modifier,
    text = Texts.excludedGenresSection()
  ) {
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(PaddingSmall),
      verticalArrangement = Arrangement.spacedBy(PaddingSmall)
    ) {
      genreChips.forEach { chip ->
        VibeInputChip(
          label = chip.name,
          selected = true,
          onDismiss = { onGenreRemove(chip.name) }
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsExcludedGenresSection(
      genreChips = genreChips,
      onGenreRemove = {}
    )
  }
}
