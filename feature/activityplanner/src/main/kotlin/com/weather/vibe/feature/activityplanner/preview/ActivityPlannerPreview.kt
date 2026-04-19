package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Error
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loaded
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loading
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.runningSelectedSegments
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.timelineHours
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.windows
import kotlinx.collections.immutable.persistentListOf

internal class ActivityPlannerPreview :
  PreviewParameterProvider<ActivityPlannerUiState> {

  private val loading: ActivityPlannerUiState =
    Loading

  private val loaded: ActivityPlannerUiState =
    Loaded(
      activities = runningSelectedSegments,
      topWindows = windows,
      timeline = timelineHours,
      emptyMessage = null
    )

  private val empty: ActivityPlannerUiState =
    Loaded(
      activities = runningSelectedSegments,
      topWindows = persistentListOf(),
      timeline = timelineHours,
      emptyMessage = "No great windows for running today."
    )

  private val error: ActivityPlannerUiState =
    Error(message = "Couldn't load activity plan.")

  override val values: Sequence<ActivityPlannerUiState> =
    sequenceOf(loading, loaded, empty, error)
}
