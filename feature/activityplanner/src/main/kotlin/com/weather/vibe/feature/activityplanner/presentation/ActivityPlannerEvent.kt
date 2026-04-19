package com.weather.vibe.feature.activityplanner.presentation

internal sealed interface ActivityPlannerEvent {
  data object NavigateBack : ActivityPlannerEvent
}
