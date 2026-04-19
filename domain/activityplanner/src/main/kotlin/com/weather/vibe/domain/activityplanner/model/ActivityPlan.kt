package com.weather.vibe.domain.activityplanner.model

data class ActivityPlan(
  val activity: ActivityType,
  val scoredHours: List<ScoredHour>,
  val topWindows: List<ScoredWindow>
)
