package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.chip.VibeInputChip
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.ui.HomeResources.Texts.genreRemoveContentDescription

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun GenreChips(
  modifier: Modifier = Modifier,
  genres: List<GenreChipUiState>,
  onThumbsDown: (String) -> Unit
) {
  FlowRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(Small),
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    genres.forEach { genre ->
      AnimatedVisibility(
        visible = !genre.isRejecting,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        VibeInputChip(
          label = genre.name,
          selected = false,
          onDismiss = { onThumbsDown(genre.name) },
          dismissContentDescription = genreRemoveContentDescription(genre.name)
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    GenreChips(
      modifier = Modifier.padding(Medium),
      genres = listOf(
        GenreChipUiState(name = "lo-fi hip hop"),
        GenreChipUiState(name = "acoustic"),
        GenreChipUiState(name = "rainy day indie")
      ),
      onThumbsDown = {}
    )
  }
}
