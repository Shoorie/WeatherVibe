package com.weather.vibe.feature.activityplanner.presentation

import com.weather.vibe.core.designsystem.components.segmented.VibeSegment
import com.weather.vibe.domain.activityplanner.model.ActivityPlan
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import com.weather.vibe.domain.activityplanner.model.ScoredWindow
import com.weather.vibe.domain.activityplanner.usecase.ClassifyScore
import com.weather.vibe.domain.activityplanner.usecase.ClassifyTemperatureComfort
import com.weather.vibe.domain.activityplanner.usecase.ClassifyUvCategory
import com.weather.vibe.domain.activityplanner.usecase.ClassifyWindCategory
import com.weather.vibe.domain.activityplanner.usecase.IsDateToday
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loaded
import com.weather.vibe.feature.activityplanner.presentation.state.TimelineHourUiState
import com.weather.vibe.feature.activityplanner.presentation.state.WindowCardUiState
import com.weather.vibe.feature.activityplanner.presentation.state.WindowMetricUiState
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Emojis
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import kotlin.math.roundToInt

@Factory
internal class ActivityPlannerStateFactory(
  private val classifyScore: ClassifyScore,
  private val classifyTemperatureComfort: ClassifyTemperatureComfort,
  private val classifyUvCategory: ClassifyUvCategory,
  private val classifyWindCategory: ClassifyWindCategory,
  private val isDateToday: IsDateToday,
  private val resources: ActivityPlannerResources
) {

  fun create(plan: ActivityPlan): ActivityPlannerUiState =
    Loaded(
      activities = allActivities(plan.activity),
      topWindows = plan.topWindows.map(::toWindowCard).toImmutableList(),
      timeline = plan.scoredHours.map(::toTimelineHour).toImmutableList(),
      emptyMessage = emptyMessageOrNull(plan)
    )

  private fun allActivities(selected: ActivityType): ImmutableList<VibeSegment<ActivityType>> =
    ActivityType.entries.map { type ->
      VibeSegment(
        value = type,
        label = resources.activityLabel(type),
        contentDescription = resources.activityContentDescription(type),
        isSelected = type == selected
      )
    }.toImmutableList()

  private fun toWindowCard(window: ScoredWindow): WindowCardUiState {

    val tier = classifyScore(window.averageScore)
    val tierLabel = resources.tierLabel(tier)
    val timeRange = formatTimeRange(window)
    val temperature = temperatureMetric(window.averageTemperature)
    val uv = uvMetric(window.averageUvIndex)
    val wind = windMetric(window.averageWindSpeed)

    return WindowCardUiState(
      timeRange = timeRange,
      tier = tier,
      tierLabel = tierLabel,
      contentDescription = resources.windowDescription(
        timeRange = timeRange,
        tierLabel = tierLabel,
        temperature = temperature.contentDescription,
        uv = uv.contentDescription,
        wind = wind.contentDescription
      ),
      temperature = temperature,
      uv = uv,
      wind = wind
    )
  }

  private fun toTimelineHour(hour: ScoredHour): TimelineHourUiState {

    val tier = classifyScore(hour.score)
    val hourLabel = resources.hour(hour.time.hour)

    return TimelineHourUiState(
      time = hour.time,
      hourLabel = hourLabel,
      contentDescription = resources.timelineHourDescription(
        hourLabel = hourLabel,
        tierLabel = resources.tierLabel(tier),
        score = hour.score
      ),
      score = hour.score,
      tier = tier
    )
  }

  private fun formatTimeRange(window: ScoredWindow): String =
    resources.timeRange(
      start = hourLabel(window.start),
      end = hourLabel(window.end)
    )

  private fun hourLabel(dateTime: LocalDateTime): String {

    val hour = dateTime.format(HOUR_MINUTE)
    if (isDateToday(dateTime.toLocalDate())) return hour

    val dayAbbreviation = dateTime.format(DAY_ABBREVIATION)
    return resources.hourWithDay(hour = hour, day = dayAbbreviation)
  }

  private fun temperatureMetric(celsius: Double): WindowMetricUiState =
    describeMetric(
      icon = Emojis.thermometer(),
      label = resources.temperatureLabel(),
      value = resources.temperature(celsius.roundToInt()),
      caption = resources.temperatureComfort(classifyTemperatureComfort(celsius))
    )

  private fun uvMetric(uvIndex: Double): WindowMetricUiState =
    describeMetric(
      icon = Emojis.sun(),
      label = resources.uvLabel(),
      value = uvIndex.roundToInt().toString(),
      caption = resources.uvCategory(classifyUvCategory(uvIndex))
    )

  private fun windMetric(kmh: Double): WindowMetricUiState =
    describeMetric(
      icon = Emojis.wind(),
      label = resources.windLabel(),
      value = resources.wind(kmh.roundToInt()),
      caption = resources.windCategory(classifyWindCategory(kmh))
    )

  private fun describeMetric(
    icon: String,
    label: String,
    value: String,
    caption: String
  ): WindowMetricUiState =
    WindowMetricUiState(
      icon = icon,
      label = label,
      value = value,
      caption = caption,
      contentDescription = resources.metricDescription(label, value, caption)
    )

  private fun emptyMessageOrNull(plan: ActivityPlan): String? =
    if (plan.topWindows.isEmpty()) resources.emptyMessage(plan.activity) else null

  private companion object {
    val HOUR_MINUTE: DateTimeFormatter = ofPattern("HH:mm")
    val DAY_ABBREVIATION: DateTimeFormatter = ofPattern("EEE")
  }
}
