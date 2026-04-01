package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.BrandIconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
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
  Column(modifier = modifier) {
    Text(
      modifier = Modifier.padding(bottom = PaddingSmall),
      text = Texts.excludedGenresSection(),
      color = colors.onSurfaceVariant,
      style = typography.labelMedium
    )
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(PaddingSmall),
      verticalArrangement = Arrangement.spacedBy(PaddingSmall)
    ) {
      genreChips.forEach { chip ->
        InputChip(
          selected = true,
          onClick = { onGenreRemove(chip.name) },
          label = {
            Text(
              text = chip.name,
              style = typography.labelSmall
            )
          },
          trailingIcon = {
            IconButton(
              onClick = { onGenreRemove(chip.name) },
              modifier = Modifier.size(BrandIconSize)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(BrandIconSize),
                tint = colors.onSurfaceVariant
              )
            }
          },
          colors = InputChipDefaults.inputChipColors(
            containerColor = colors.glassSurface,
            labelColor = colors.onBackground,
            selectedContainerColor = colors.accent,
            selectedLabelColor = colors.onBackground
          ),
          border = InputChipDefaults.inputChipBorder(
            enabled = true,
            selected = true,
            borderColor = colors.accent,
            selectedBorderColor = colors.accent
          )
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
