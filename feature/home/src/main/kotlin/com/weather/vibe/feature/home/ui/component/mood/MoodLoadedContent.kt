package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreviewData

@Composable
internal fun MoodLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  onGenreRemoveClick: (String) -> Unit,
  onOpenSpotify: (String) -> Unit,
  onOpenYtMusic: (String) -> Unit
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = state.mood,
      style = typography.bodyMedium,
      color = colors.onBackground
    )
    Spacer(modifier = Modifier.height(ExtraSmall))
    Text(
      text = state.moodDescription,
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(Medium))
    GenreChips(
      genres = state.genres,
      onThumbsDown = onGenreRemoveClick
    )
    Spacer(modifier = Modifier.height(Medium))
    SpotifyButton(onClick = { onOpenSpotify(state.spotifyQuery) })
    Spacer(modifier = Modifier.height(Small))
    YtMusicButton(onClick = { onOpenYtMusic(state.ytMusicUrl) })
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    MoodLoadedContent(
      modifier = Modifier.padding(Medium),
      state = HomePreviewData.loadedPlaylist,
      onGenreRemoveClick = {},
      onOpenSpotify = {},
      onOpenYtMusic = {}
    )
  }
}
