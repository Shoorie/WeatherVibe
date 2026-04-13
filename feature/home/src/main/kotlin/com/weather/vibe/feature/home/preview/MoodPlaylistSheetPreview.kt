package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Error
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist

internal class MoodPlaylistSheetPreview :
  PreviewParameterProvider<PlaylistUiState> {

  private val generating: PlaylistUiState =
    Generating(message = "Finding better suggestions\u2026")

  override val values: Sequence<PlaylistUiState> =
    sequenceOf(Loading, loadedPlaylist, generating, Error)
}
