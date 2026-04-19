package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.activityplanner.presentation.state.WindowCardUiState
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.excellentWindow
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.goodWindow

internal class WindowCardPreview :
  PreviewParameterProvider<WindowCardUiState> {

  override val values: Sequence<WindowCardUiState> =
    sequenceOf(excellentWindow, goodWindow)
}
