package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Error
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loading

internal class MoodPlaylistSheetPreview :
  PreviewParameterProvider<PlaylistUiState> {

  private val loading: PlaylistUiState = Loading

  private val loaded: PlaylistUiState =
    Loaded(
      genres = listOf("lo-fi hip hop", "acoustic", "rainy day indie"),
      mood = "Cozy rainy afternoon, stay in",
      spotifyQuery = "spotify:search:lo-fi hip hop acoustic rainy day indie",
      ytMusicUrl = "https://music.youtube.com/search?q=lo-fi+hip+hop+acoustic+rainy+day+indie"
    )

  private val error: PlaylistUiState = Error

  override val values: Sequence<PlaylistUiState> =
    sequenceOf(loading, loaded, error)
}
