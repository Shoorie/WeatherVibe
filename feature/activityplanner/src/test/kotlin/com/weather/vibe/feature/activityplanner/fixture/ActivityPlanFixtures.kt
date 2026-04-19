package com.weather.vibe.feature.activityplanner.fixture

import com.weather.vibe.domain.activityplanner.model.ActivityPlan
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import com.weather.vibe.domain.activityplanner.model.ScoredWindow
import java.time.LocalDateTime

internal object ActivityPlanFixtures {

  val WINDOW_START: LocalDateTime = LocalDateTime.of(2026, 4, 8, 16, 0)
  val WINDOW_END: LocalDateTime = LocalDateTime.of(2026, 4, 8, 18, 0)
  const val DEFAULT_AVERAGE_SCORE = 80
  const val DEFAULT_AVERAGE_TEMPERATURE = 18.0
  const val DEFAULT_AVERAGE_UV = 2.0
  const val DEFAULT_AVERAGE_WIND = 8.0
  const val DEFAULT_MAX_PRECIPITATION = 10

  fun plan(
    activity: ActivityType = ActivityType.RUNNING,
    hours: List<ScoredHour> = emptyList(),
    windows: List<ScoredWindow> = emptyList()
  ): ActivityPlan = ActivityPlan(
    activity = activity,
    scoredHours = hours,
    topWindows = windows
  )

  fun window(
    start: LocalDateTime = WINDOW_START,
    end: LocalDateTime = WINDOW_END,
    averageScore: Int = DEFAULT_AVERAGE_SCORE
  ): ScoredWindow = ScoredWindow(
    start = start,
    end = end,
    averageScore = averageScore,
    averageTemperature = DEFAULT_AVERAGE_TEMPERATURE,
    averageUvIndex = DEFAULT_AVERAGE_UV,
    averageWindSpeed = DEFAULT_AVERAGE_WIND,
    maxPrecipitationProbability = DEFAULT_MAX_PRECIPITATION
  )
}
