package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.activityplanner.model.ActivityPlan
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory

@Factory
class BuildActivityPlan(
  private val approximateHourlyUvIndex: ApproximateHourlyUvIndex,
  private val findBestWindows: FindBestWindows,
  private val scoreHourForActivity: ScoreHourForActivity,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(weather: WeatherData, activity: ActivityType): ActivityPlan {

    val scoredHours = weather.hourlyForecast
      .sortedBy(HourlyWeather::time)
      .filter(::isWithinNext24Hours)
      .map { hour -> scoreHour(hour, weather.dailyForecast, activity) }

    return ActivityPlan(
      activity = activity,
      scoredHours = scoredHours,
      topWindows = findBestWindows(scoredHours)
    )
  }

  private fun isWithinNext24Hours(hour: HourlyWeather): Boolean {
    val now = timeProvider.now()
    val horizon = now.plusHours(WINDOW_HOURS)
    return !hour.time.isBefore(now) && hour.time.isBefore(horizon)
  }

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
