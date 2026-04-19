package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.activityplanner.presentation.state.WindowMetricUiState
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.temperatureMetric
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.uvMetric
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.windMetric

internal class WindowMetricTilePreview :
  PreviewParameterProvider<WindowMetricUiState> {

  override val values: Sequence<WindowMetricUiState> =
    sequenceOf(temperatureMetric, uvMetric, windMetric)
}
