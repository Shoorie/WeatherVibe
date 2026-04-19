package com.weather.vibe.feature.activityplanner.presentation.fixture

import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort
import com.weather.vibe.domain.activityplanner.model.UvCategory
import com.weather.vibe.domain.activityplanner.model.WindCategory

internal object ActivityPlannerResourcesFixtures {

  const val DEFAULT_ERROR = "default-error"
  const val TEMPERATURE_LABEL = "Temperature"
  const val UV_LABEL = "UV"
  const val WIND_LABEL = "Wind"
  const val TOP_WINDOWS = "top-windows"
  const val TIMELINE = "timeline"
  const val RETRY = "retry"
  const val LEGEND_EXCELLENT = "legend-excellent"
  const val LEGEND_GOOD = "legend-good"
  const val LEGEND_FAIR = "legend-fair"
  const val LEGEND_POOR = "legend-poor"

  fun activityLabel(type: ActivityType): String =
    "activity-label-${type.name}"

  fun activityContentDescription(type: ActivityType): String =
    "activity-cd-${type.name}"

  fun emptyMessage(type: ActivityType): String =
    "empty-${type.name}"

  fun tierLabel(tier: ScoreTier): String =
    "tier-${tier.name}"

  fun temperatureComfort(comfort: TemperatureComfort): String =
    "comfort-${comfort.name}"

  fun uvCategory(category: UvCategory): String =
    "uv-${category.name}"

  fun windCategory(category: WindCategory): String =
    "wind-${category.name}"

  fun temperature(celsius: Int): String =
    "$celsius°C"

  fun wind(kmh: Int): String =
    "$kmh km/h"

  fun hour(hour: Int): String =
    "$hour"

  fun timeRange(start: String, end: String): String =
    "$start – $end"

  fun hourWithDay(hour: String, day: String): String =
    "$hour ($day)"

  fun metricDescription(label: String, value: String, caption: String): String =
    "$label: $value, $caption"

  fun windowDescription(
    timeRange: String,
    tierLabel: String,
    temperature: String,
    uv: String,
    wind: String
  ): String =
    "$timeRange, $tierLabel. $temperature. $uv. $wind."

  fun timelineHourDescription(hourLabel: String, tierLabel: String, score: Int): String =
    "$hourLabel / $tierLabel / $score"
}
