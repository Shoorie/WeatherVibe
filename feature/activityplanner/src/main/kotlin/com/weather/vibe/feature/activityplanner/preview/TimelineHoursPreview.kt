package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.activityplanner.presentation.state.TimelineHourUiState
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.timelineHours
import kotlinx.collections.immutable.ImmutableList

internal class TimelineHoursPreview :
  PreviewParameterProvider<ImmutableList<TimelineHourUiState>> {

  override val values: Sequence<ImmutableList<TimelineHourUiState>> =
    sequenceOf(timelineHours)
}
