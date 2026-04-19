package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loaded
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.runningSelectedSegments
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.timelineHours
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerPreviewData.windows
import kotlinx.collections.immutable.persistentListOf

internal class ActivityPlannerLoadedPreview : PreviewParameterProvider<Loaded> {

  private val withWindows: Loaded =
    Loaded(
      activities = runningSelectedSegments,
      topWindows = windows,
      timeline = timelineHours,
      emptyMessage = null
    )

  private val withoutWindows: Loaded =
    Loaded(
      activities = runningSelectedSegments,
      topWindows = persistentListOf(),
      timeline = timelineHours,
      emptyMessage = "No great windows for running today."
    )

  override val values: Sequence<Loaded> =
    sequenceOf(withWindows, withoutWindows)
}
