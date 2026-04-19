package com.weather.vibe.domain.activityplanner.fixture

import com.weather.vibe.domain.activityplanner.model.ScoreReason
import com.weather.vibe.domain.activityplanner.model.ScoredHour
import java.time.LocalDateTime

internal object ScoredHourFixtures {

  const val DEFAULT_SCORE = 70
  const val DEFAULT_TEMPERATURE = 18.0
  const val DEFAULT_UV_INDEX = 3.0
  const val DEFAULT_WIND_SPEED = 10.0
  const val DEFAULT_PRECIPITATION = 10

  val DEFAULT_TIME: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)

  fun scoredHour(
    time: LocalDateTime = DEFAULT_TIME,
    score: Int = DEFAULT_SCORE,
    temperature: Double = DEFAULT_TEMPERATURE,
    uvIndex: Double = DEFAULT_UV_INDEX,
    windSpeed: Double = DEFAULT_WIND_SPEED,
    precipitationProbability: Int = DEFAULT_PRECIPITATION,
    reasons: List<ScoreReason> = emptyList()
  ): ScoredHour = ScoredHour(
    time = time,
    score = score,
    temperature = temperature,
    uvIndex = uvIndex,
    windSpeed = windSpeed,
    precipitationProbability = precipitationProbability,
    reasons = reasons
  )

  fun scoredHourAt(hour: Int, score: Int): ScoredHour =
    scoredHour(time = DEFAULT_TIME.withHour(hour), score = score)
}
