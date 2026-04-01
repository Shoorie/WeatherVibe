package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loading

internal class MoodPlaylistSheetPreview :
  PreviewParameterProvider<PlaylistUiState> {

  private val loading: PlaylistUiState = Loading

  private val loaded: PlaylistUiState =
    Loaded(
      genres = listOf(
        GenreChipUiState(name = "lo-fi hip hop"),
        GenreChipUiState(name = "acoustic"),
        GenreChipUiState(name = "rainy day indie")
      ),
      mood = "Cozy rainy afternoon",
      moodDescription = "Stay in, grab a warm drink, and let the music match the rain",
      spotifyQuery = "spotify:search:lo-fi hip hop acoustic rainy day indie",
      ytMusicUrl = "https://music.youtube.com/search?q=lo-fi+hip+hop"
    )

  private val generating: PlaylistUiState =
    Generating(message = "Finding better suggestions\u2026")

  private val error: PlaylistUiState = PlaylistUiState.Error

  override val values: Sequence<PlaylistUiState> =
    sequenceOf(loading, loaded, generating, error)
}
