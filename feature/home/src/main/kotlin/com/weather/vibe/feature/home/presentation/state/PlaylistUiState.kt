package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface PlaylistUiState {

  @Immutable
  data object Error : PlaylistUiState

  @Immutable
  data class Loaded(
    val genres: List<String>,
    val mood: String,
    val spotifyQuery: String,
    val ytMusicUrl: String
  ) : PlaylistUiState

  @Immutable
  data object Loading : PlaylistUiState
}
