package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreviewData

@Composable
internal fun MoodLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  onGenreRemoveClick: (String) -> Unit,
  onOpenSpotify: (String, String) -> Unit,
  onOpenYtMusic: (String) -> Unit
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    MoodHeroCard(
      mood = state.mood,
      moodDescription = state.moodDescription
    )
    GenreChips(
      genres = state.genres,
      onThumbsDown = onGenreRemoveClick
    )
    Column(verticalArrangement = Arrangement.spacedBy(Small)) {
      SpotifyButton(onClick = { onOpenSpotify(state.spotifyAppUri, state.spotifyWebUrl) })
      YtMusicButton(onClick = { onOpenYtMusic(state.ytMusicUrl) })
    }
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
      onOpenSpotify = { _, _ -> },
      onOpenYtMusic = {}
    )
  }
}
