package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionHeader
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Error
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Generating
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState.Loading
import com.weather.vibe.feature.home.preview.MoodPlaylistSheetPreview
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.headphones
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistSubtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoodPlaylistSheet(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  onGenreRemoveClick: (String) -> Unit,
  onOpenSpotify: (String) -> Unit,
  onOpenYtMusic: (String) -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
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
        .padding(horizontal = Medium)
        .padding(bottom = Large),
      verticalArrangement = Arrangement.spacedBy(Medium)
    ) {
      SectionHeader(
        emoji = headphones(),
        title = moodPlaylistLabel(),
        subtitle = moodPlaylistSubtitle(),
        titleTextStyle = typography.titleMedium
      )
      MoodSheetContent(
        state = state,
        onGenreRemoveClick = onGenreRemoveClick,
        onOpenSpotify = onOpenSpotify,
        onOpenYtMusic = onOpenYtMusic
      )
    }
  }
}

@Composable
private fun MoodSheetContent(
  state: PlaylistUiState,
  onGenreRemoveClick: (String) -> Unit,
  onOpenSpotify: (String) -> Unit,
  onOpenYtMusic: (String) -> Unit
) {
  when (state) {
    is Loading -> MoodLoadingContent()
    is Loaded -> MoodLoadedContent(
      state = state,
      onGenreRemoveClick = onGenreRemoveClick,
      onOpenSpotify = onOpenSpotify,
      onOpenYtMusic = onOpenYtMusic
    )

    is Generating -> MoodGeneratingContent(message = state.message)
    is Error -> MoodErrorContent()
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
