package com.weather.vibe.feature.activityplanner.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.core.designsystem.components.segmented.VibeSegment
import com.weather.vibe.domain.activityplanner.model.ActivityType
import kotlinx.collections.immutable.ImmutableList

internal sealed interface ActivityPlannerUiState {

  @Immutable
  data object Loading : ActivityPlannerUiState

  @Immutable
  data class Loaded(
    val activities: ImmutableList<VibeSegment<ActivityType>>,
    val topWindows: ImmutableList<WindowCardUiState>,
    val timeline: ImmutableList<TimelineHourUiState>,
    val emptyMessage: String?
  ) : ActivityPlannerUiState

  @Immutable
  data class Error(val message: String) : ActivityPlannerUiState
}
