package com.weather.vibe.feature.activityplanner.preview

import com.weather.vibe.core.designsystem.components.segmented.VibeSegment
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.feature.activityplanner.presentation.state.TimelineHourUiState
import com.weather.vibe.feature.activityplanner.presentation.state.WindowCardUiState
import com.weather.vibe.feature.activityplanner.presentation.state.WindowMetricUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDateTime

internal object ActivityPlannerPreviewData {

  private val SAMPLE_DAY: LocalDateTime =
    LocalDateTime.of(2026, 4, 13, 0, 0)

  val runningSelectedSegments: ImmutableList<VibeSegment<ActivityType>> =
    persistentListOf(
      VibeSegment(
        value = ActivityType.RUNNING,
        label = "🏃 Running",
        contentDescription = "Running",
        isSelected = true
      ),
      VibeSegment(
        value = ActivityType.WALKING,
        label = "🚶 Walking",
        contentDescription = "Walking",
        isSelected = false
      ),
      VibeSegment(
        value = ActivityType.CYCLING,
        label = "🚴 Cycling",
        contentDescription = "Cycling",
        isSelected = false
      )
    )

  val temperatureMetric: WindowMetricUiState =
    WindowMetricUiState(
      icon = "🌡",
      label = "Temperature",
      value = "18°C",
      caption = "Comfy",
      contentDescription = "Temperature: 18°C, Comfy"
    )

  val uvMetric: WindowMetricUiState =
    WindowMetricUiState(
      icon = "☀",
      label = "UV index",
      value = "2",
      caption = "Low",
      contentDescription = "UV index: 2, Low"
    )

  val windMetric: WindowMetricUiState =
    WindowMetricUiState(
      icon = "💨",
      label = "Wind",
      value = "8 km/h",
      caption = "Calm",
      contentDescription = "Wind: 8 km/h, Calm"
    )

  val excellentWindow: WindowCardUiState =
    WindowCardUiState(
      timeRange = "16:00 – 18:00",
      tier = ScoreTier.EXCELLENT,
      tierLabel = "Excellent",
      contentDescription = "16:00 to 18:00, Excellent",
      temperature = temperatureMetric,
      uv = uvMetric,
      wind = windMetric
    )

  val goodWindow: WindowCardUiState =
    excellentWindow.copy(
      timeRange = "07:00 – 09:00",
      tier = ScoreTier.GOOD,
      tierLabel = "Good",
      contentDescription = "07:00 to 09:00, Good"
    )

  val windows: ImmutableList<WindowCardUiState> =
    persistentListOf(excellentWindow, goodWindow)

  val timelineHours: ImmutableList<TimelineHourUiState> =
    (10..20).map { hour ->
      TimelineHourUiState(
        time = SAMPLE_DAY.withHour(hour),
        hourLabel = hour.toString(),
        contentDescription = "$hour o'clock, score ${40 + hour * 2}",
        score = 40 + hour * 2,
        tier = if (hour > 14) ScoreTier.EXCELLENT else ScoreTier.FAIR
      )
    }.toImmutableList()
}
