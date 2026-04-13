package com.weather.vibe.feature.home.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.button.BrandButton
import com.weather.vibe.core.designsystem.components.chip.VibeInputChip
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Error
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loading
import com.weather.vibe.feature.home.preview.MoodPlaylistSheetPreview
import com.weather.vibe.feature.home.ui.HomeResources.Painters.spotifyIcon
import com.weather.vibe.feature.home.ui.HomeResources.Painters.ytMusicIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.genreRemoveContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistUnavailable
import com.weather.vibe.feature.home.ui.HomeResources.Texts.openInSpotify
import com.weather.vibe.feature.home.ui.HomeResources.Texts.openInYtMusic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoodPlaylistSheet(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  onGenreRemoveClick: (String) -> Unit,
  onOpenSpotify: (String) -> Unit,
  onOpenYtMusic: (String) -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(),
  state: PlaylistUiState
) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = colors.sheetSurface,
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = Padding.Medium)
        .padding(bottom = Padding.Large)
    ) {
      Text(
        text = moodPlaylistLabel(),
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Spacer(modifier = Modifier.height(Padding.Medium))
      when (state) {
        is Loading -> SheetLoadingContent()
        is Loaded -> SheetLoadedContent(
          onGenreRemoveClick = onGenreRemoveClick,
          onOpenSpotify = onOpenSpotify,
          onOpenYtMusic = onOpenYtMusic,
          state = state
        )
        is Generating -> SheetGeneratingContent(message = state.message)
        is Error -> SheetErrorContent()
      }
    }
  }
}

@Composable
private fun SheetLoadingContent(modifier: Modifier = Modifier) {
  LoadingIndicator(
    modifier = modifier
      .fillMaxWidth()
      .height(Padding.Large)
  )
}

@Composable
private fun SheetGeneratingContent(
  modifier: Modifier = Modifier,
  message: String
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator(color = colors.accent)
    Spacer(modifier = Modifier.height(Padding.Small))
    Text(
      text = message,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
  }
}

@Composable
private fun SheetErrorContent() {
  Text(
    text = moodPlaylistUnavailable(),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun SheetLoadedContent(
  modifier: Modifier = Modifier,
  onGenreRemoveClick: (String) -> Unit,
  onOpenSpotify: (String) -> Unit,
  onOpenYtMusic: (String) -> Unit,
  state: Loaded
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = state.mood,
      style = typography.bodyMedium,
      color = colors.onBackground
    )
    Spacer(modifier = Modifier.height(Padding.ExtraSmall))
    Text(
      text = state.moodDescription,
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(Padding.Medium))
    GenreChips(
      genres = state.genres,
      onThumbsDown = onGenreRemoveClick
    )
    Spacer(modifier = Modifier.height(Padding.Medium))
    SpotifyButton(onClick = { onOpenSpotify(state.spotifyQuery) })
    Spacer(modifier = Modifier.height(Padding.Small))
    YtMusicButton(onClick = { onOpenYtMusic(state.ytMusicUrl) })
  }
}

@Composable
private fun SpotifyButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  BrandButton(
    modifier = modifier,
    icon = spotifyIcon(),
    text = openInSpotify(),
    containerColor = SpotifyGreen,
    onClick = onClick
  )
}

@Composable
private fun YtMusicButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  BrandButton(
    modifier = modifier,
    icon = ytMusicIcon(),
    text = openInYtMusic(),
    containerColor = YtMusicRed,
    onClick = onClick
  )
}

private val SpotifyGreen = Color(0xFF1DB954)
private val YtMusicRed = Color(0xFFFF0000)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenreChips(
  modifier: Modifier = Modifier,
  genres: List<GenreChipUiState>,
  onThumbsDown: (String) -> Unit
) {
  FlowRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(Padding.Small),
    verticalArrangement = Arrangement.spacedBy(Padding.Small)
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

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(MoodPlaylistSheetPreview::class)
  state: PlaylistUiState
) {
  WeatherVibeTheme {
    MoodPlaylistSheet(
      onDismiss = {},
      onGenreRemoveClick = {},
      onOpenSpotify = {},
      onOpenYtMusic = {},
      state = state
    )
  }
}
