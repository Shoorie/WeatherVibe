package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.BrandIconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Error
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loading
import com.weather.vibe.feature.home.preview.MoodPlaylistSheetPreview
import com.weather.vibe.feature.home.ui.HomeResources.Painters.spotifyIcon
import com.weather.vibe.feature.home.ui.HomeResources.Painters.ytMusicIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistUnavailable
import com.weather.vibe.feature.home.ui.HomeResources.Texts.openInSpotify
import com.weather.vibe.feature.home.ui.HomeResources.Texts.openInYtMusic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoodPlaylistSheet(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
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
        .padding(horizontal = PaddingMedium)
        .padding(bottom = PaddingLarge)
    ) {
      Text(
        text = moodPlaylistLabel(),
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Spacer(modifier = Modifier.height(PaddingMedium))
      when (state) {
        is Loading -> SheetLoadingContent()
        is Error -> SheetErrorContent()
        is Loaded -> SheetLoadedContent(
          state = state,
          onOpenSpotify = onOpenSpotify,
          onOpenYtMusic = onOpenYtMusic
        )
      }
    }
  }
}

@Composable
private fun SheetLoadingContent(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(PaddingLarge),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(color = colors.accent)
  }
}

@Composable
private fun SheetErrorContent(modifier: Modifier = Modifier) {
  Text(
    modifier = modifier.fillMaxWidth(),
    text = moodPlaylistUnavailable(),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun SheetLoadedContent(
  modifier: Modifier = Modifier,
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
    Spacer(modifier = Modifier.height(PaddingMedium))
    GenreChips(genres = state.genres)
    Spacer(modifier = Modifier.height(PaddingMedium))
    SpotifyButton(onClick = { onOpenSpotify(state.spotifyQuery) })
    Spacer(modifier = Modifier.height(PaddingSmall))
    YtMusicButton(onClick = { onOpenYtMusic(state.ytMusicUrl) })
  }
}

@Composable
private fun SpotifyButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    modifier = modifier.fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(containerColor = SpotifyGreen)
  ) {
    Icon(
      painter = spotifyIcon(),
      contentDescription = null,
      modifier = Modifier.size(BrandIconSize),
      tint = colors.onBackground
    )
    Spacer(modifier = Modifier.width(PaddingExtraSmall))
    Text(
      text = openInSpotify(),
      color = colors.onBackground
    )
  }
}

@Composable
private fun YtMusicButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    modifier = modifier.fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(containerColor = YtMusicRed)
  ) {
    Icon(
      painter = ytMusicIcon(),
      contentDescription = null,
      modifier = Modifier.size(BrandIconSize),
      tint = colors.onBackground
    )
    Spacer(modifier = Modifier.width(PaddingExtraSmall))
    Text(
      text = openInYtMusic(),
      color = colors.onBackground
    )
  }
}

private val SpotifyGreen = Color(0xFF1DB954)
private val YtMusicRed = Color(0xFFFF0000)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenreChips(
  modifier: Modifier = Modifier,
  genres: List<String>
) {
  FlowRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(PaddingSmall),
    verticalArrangement = Arrangement.spacedBy(PaddingSmall)
  ) {
    genres.forEach { genre ->
      SuggestionChip(
        onClick = {},
        label = {
          Text(
            text = genre,
            style = typography.labelSmall
          )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
          containerColor = colors.glassSurface,
          labelColor = colors.onBackground
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
          enabled = true,
          borderColor = colors.outline
        )
      )
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
      onOpenSpotify = {},
      onOpenYtMusic = {},
      state = state
    )
  }
}
