package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed interface PlaylistUiState {

  @Immutable
  data object Error : PlaylistUiState

  @Immutable
  data class Generating(val message: String) : PlaylistUiState

  @Immutable
  data class Loaded(
    val genres: ImmutableList<GenreChipUiState>,
    val mood: String,
    val moodDescription: String,
    val spotifyAppUri: String,
    val spotifyWebUrl: String,
    val ytMusicUrl: String
  ) : PlaylistUiState

  @Immutable
  data object Loading : PlaylistUiState
}
