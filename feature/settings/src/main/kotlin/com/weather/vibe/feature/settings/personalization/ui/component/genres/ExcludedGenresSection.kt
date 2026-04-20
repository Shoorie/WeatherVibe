package com.weather.vibe.feature.settings.personalization.ui.component.genres

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
import com.weather.vibe.feature.settings.personalization.presentation.state.GenreChipUiState
import com.weather.vibe.feature.settings.personalization.preview.PersonalizationPreviewData.genreChips
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Emojis
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.excludedGenresSection
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.excludedGenresSectionSubtitle
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.genreRemoveContentDescription
import com.weather.vibe.feature.settings.shared.ui.component.SettingsSection
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ExcludedGenresSection(
  modifier: Modifier = Modifier,
  genreChips: ImmutableList<GenreChipUiState>,
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
