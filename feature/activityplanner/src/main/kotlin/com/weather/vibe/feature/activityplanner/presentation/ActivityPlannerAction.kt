package com.weather.vibe.feature.activityplanner.presentation

import com.weather.vibe.domain.activityplanner.model.ActivityType

internal sealed interface ActivityPlannerAction {
  data class ActivitySelect(val type: ActivityType) : ActivityPlannerAction
  data object BackClick : ActivityPlannerAction
  data object RetryClick : ActivityPlannerAction
}
