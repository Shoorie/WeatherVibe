package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.activityplanner.presentation.state.WindowCardUiState
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.windows
import kotlinx.collections.immutable.ImmutableList

internal class TopWindowsPreview :
  PreviewParameterProvider<ImmutableList<WindowCardUiState>> {

  override val values: Sequence<ImmutableList<WindowCardUiState>> =
    sequenceOf(windows)
}
