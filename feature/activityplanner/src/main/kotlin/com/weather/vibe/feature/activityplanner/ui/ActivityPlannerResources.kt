package com.weather.vibe.feature.activityplanner.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort
import com.weather.vibe.domain.activityplanner.model.UvCategory
import com.weather.vibe.domain.activityplanner.model.WindCategory
import com.weather.vibe.feature.activityplanner.R
import org.koin.core.annotation.Factory

@Factory
internal class ActivityPlannerResources(private val context: Context) {

  fun activityLabel(type: ActivityType): String =
    context.getString(labelResId(type))

  fun activityContentDescription(type: ActivityType): String =
    context.getString(a11yLabelResId(type))

  fun emptyMessage(type: ActivityType): String =
    context.getString(emptyMessageResId(type))

  fun timeRange(start: String, end: String): String =
    context.getString(R.string.activityplanner_time_range_format, start, end)

  fun hourWithDay(hour: String, day: String): String =
    context.getString(R.string.activityplanner_hour_with_day_format, hour, day)

  fun temperature(celsius: Int): String =
    context.getString(R.string.activityplanner_temperature_format, celsius)

  fun uv(index: Int): String =
    context.getString(R.string.activityplanner_uv_format, index)

  fun wind(kmh: Int): String =
    context.getString(R.string.activityplanner_wind_format, kmh)

  fun hour(hour: Int): String =
    context.getString(R.string.activityplanner_hour_format, hour)

  fun timelineHourDescription(hourLabel: String, tierLabel: String, score: Int): String =
    context.getString(R.string.activityplanner_timeline_hour_cd_format, hourLabel, tierLabel, score)

  fun windowDescription(
    timeRange: String,
    tierLabel: String,
    temperature: String,
    uv: String,
    wind: String
  ): String = context.getString(
    R.string.activityplanner_window_cd_format,
    timeRange,
    tierLabel,
    temperature,
    uv,
    wind
  )

  fun metricDescription(label: String, value: String, caption: String): String =
    context.getString(R.string.activityplanner_metric_cd_format, label, value, caption)

  fun defaultError(): String =
    context.getString(R.string.activityplanner_default_error)

  fun tierLabel(tier: ScoreTier): String =
    context.getString(tierLabelResId(tier))

  fun temperatureLabel(): String =
    context.getString(R.string.activityplanner_temperature_label)

  fun uvLabel(): String =
    context.getString(R.string.activityplanner_uv_label)

  fun windLabel(): String =
    context.getString(R.string.activityplanner_wind_label)

  fun temperatureComfort(comfort: TemperatureComfort): String =
    context.getString(comfortResId(comfort))

  fun uvCategory(category: UvCategory): String =
    context.getString(uvCategoryResId(category))

  fun windCategory(category: WindCategory): String =
    context.getString(windCategoryResId(category))

  private fun tierLabelResId(tier: ScoreTier): Int =
    when (tier) {
      ScoreTier.EXCELLENT -> R.string.activityplanner_tier_excellent
      ScoreTier.GOOD -> R.string.activityplanner_tier_good
      ScoreTier.FAIR -> R.string.activityplanner_tier_fair
      ScoreTier.POOR -> R.string.activityplanner_tier_poor
    }

  private fun comfortResId(comfort: TemperatureComfort): Int =
    when (comfort) {
      TemperatureComfort.COLD -> R.string.activityplanner_temp_comfort_cold
      TemperatureComfort.CHILLY -> R.string.activityplanner_temp_comfort_chilly
      TemperatureComfort.COMFY -> R.string.activityplanner_temp_comfort_comfy
      TemperatureComfort.WARM -> R.string.activityplanner_temp_comfort_warm
      TemperatureComfort.HOT -> R.string.activityplanner_temp_comfort_hot
    }

  private fun uvCategoryResId(category: UvCategory): Int =
    when (category) {
      UvCategory.LOW -> R.string.activityplanner_uv_category_low
      UvCategory.MODERATE -> R.string.activityplanner_uv_category_moderate
      UvCategory.HIGH -> R.string.activityplanner_uv_category_high
      UvCategory.VERY_HIGH -> R.string.activityplanner_uv_category_very_high
    }

  private fun windCategoryResId(category: WindCategory): Int =
    when (category) {
      WindCategory.CALM -> R.string.activityplanner_wind_category_calm
      WindCategory.BREEZY -> R.string.activityplanner_wind_category_breezy
      WindCategory.WINDY -> R.string.activityplanner_wind_category_windy
      WindCategory.GUSTY -> R.string.activityplanner_wind_category_gusty
    }

  private fun labelResId(type: ActivityType): Int =
    when (type) {
      ActivityType.RUNNING -> R.string.activityplanner_activity_running
      ActivityType.WALKING -> R.string.activityplanner_activity_walking
      ActivityType.CYCLING -> R.string.activityplanner_activity_cycling
    }

  private fun a11yLabelResId(type: ActivityType): Int =
    when (type) {
      ActivityType.RUNNING -> R.string.activityplanner_activity_running_a11y
      ActivityType.WALKING -> R.string.activityplanner_activity_walking_a11y
      ActivityType.CYCLING -> R.string.activityplanner_activity_cycling_a11y
    }

  private fun emptyMessageResId(type: ActivityType): Int =
    when (type) {
      ActivityType.RUNNING -> R.string.activityplanner_empty_running
      ActivityType.WALKING -> R.string.activityplanner_empty_walking
      ActivityType.CYCLING -> R.string.activityplanner_empty_cycling
    }

  object Texts {

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.activityplanner_screen_title)

    @Composable
    fun screenSubtitle(): String =
      stringResource(R.string.activityplanner_screen_subtitle)

    @Composable
    fun topWindows(): String =
      stringResource(R.string.activityplanner_top_windows)

    @Composable
    fun timeline(): String =
      stringResource(R.string.activityplanner_timeline)

    @Composable
    fun retry(): String =
      stringResource(R.string.activityplanner_retry)

    @Composable
    fun legendExcellent(): String =
      stringResource(R.string.activityplanner_timeline_legend_excellent)

    @Composable
    fun legendGood(): String =
      stringResource(R.string.activityplanner_timeline_legend_good)

    @Composable
    fun legendFair(): String =
      stringResource(R.string.activityplanner_timeline_legend_fair)

    @Composable
    fun legendPoor(): String =
      stringResource(R.string.activityplanner_timeline_legend_poor)
  }

  object Emojis {
    fun thermometer(): String = "🌡"
    fun sun(): String = "☀"
    fun wind(): String = "💨"
  }
}
