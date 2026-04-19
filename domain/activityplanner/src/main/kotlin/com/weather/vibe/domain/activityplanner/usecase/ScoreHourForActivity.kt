package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPreferences.Companion.forActivity
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import com.weather.vibe.domain.weather.model.HourlyWeather
import org.koin.core.annotation.Factory

@Factory
class ScoreHourForActivity(
  private val calculateHourPenalty: CalculateHourPenalty,
  private val collectScoreReasons: CollectScoreReasons
) {

  operator fun invoke(
    hour: HourlyWeather,
    uvIndex: Double,
    activity: ActivityType
  ): ScoredHour {

    val preferences = forActivity(activity)
    val penalty = calculateHourPenalty(hour, uvIndex, preferences)
    val reasons = collectScoreReasons(hour, uvIndex, preferences)

    return ScoredHour(
      time = hour.time,
      score = (MAX_SCORE - penalty).coerceIn(MIN_SCORE, MAX_SCORE),
      temperature = hour.temperature,
      uvIndex = uvIndex,
      windSpeed = hour.windSpeed,
      precipitationProbability = hour.precipitationProbability,
      reasons = reasons
    )
  }

  private companion object {
    const val MIN_SCORE = 0
    const val MAX_SCORE = 100
  }
}
