package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPlan
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.HOURS

@Factory
class BuildActivityPlan(
  private val approximateHourlyUvIndex: ApproximateHourlyUvIndex,
  private val findBestWindows: FindBestWindows,
  private val scoreHourForActivity: ScoreHourForActivity
) {

  operator fun invoke(weather: WeatherData, activity: ActivityType): ActivityPlan {

    val forecast = weather.hourlyForecast
      .sortedBy(HourlyWeather::time)

    val windowStart = forecast.firstHour()
      ?: return emptyPlanFor(activity)

    val scoredHours = forecast
      .within24HoursFrom(windowStart)
      .map { hour -> scoreHour(hour, weather.dailyForecast, activity) }

    return ActivityPlan(
      activity = activity,
      scoredHours = scoredHours,
      topWindows = findBestWindows(scoredHours)
    )
  }

  private fun List<HourlyWeather>.firstHour(): LocalDateTime? =
    firstOrNull()?.time?.truncatedTo(HOURS)

  private fun List<HourlyWeather>.within24HoursFrom(
    start: LocalDateTime
  ): List<HourlyWeather> {
    val horizon = start.plusHours(WINDOW_HOURS)
    return filter { it.time.isBefore(horizon) }
  }

  private fun emptyPlanFor(activity: ActivityType): ActivityPlan =
    ActivityPlan(
      activity = activity,
      scoredHours = emptyList(),
      topWindows = emptyList()
    )

  private fun scoreHour(
    hour: HourlyWeather,
    dailyForecast: List<DailyWeather>,
    activity: ActivityType
  ): ScoredHour =
    scoreHourForActivity(
      hour = hour,
      uvIndex = uvIndexFor(hour, dailyForecast),
      activity = activity
    )

  private fun uvIndexFor(
    hour: HourlyWeather,
    dailyForecast: List<DailyWeather>
  ): Double {

    val day = dailyForecast
      .firstOrNull { it.date == hour.time.toLocalDate() }

    return approximateHourlyUvIndex(
      hour = hour.time,
      sunrise = day?.sunrise,
      sunset = day?.sunset,
      dailyMaxUvIndex = day?.uvIndexMax ?: 0.0
    )
  }

  private companion object {
    const val WINDOW_HOURS = 24L
  }
}
