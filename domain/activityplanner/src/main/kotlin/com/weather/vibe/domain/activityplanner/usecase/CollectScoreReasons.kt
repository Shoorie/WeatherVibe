package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPreferences
import com.weather.vibe.domain.activityplanner.model.ScoreReason
import com.weather.vibe.domain.activityplanner.model.ScoreReason.PrecipitationDry
import com.weather.vibe.domain.activityplanner.model.ScoreReason.PrecipitationLikely
import com.weather.vibe.domain.activityplanner.model.ScoreReason.TemperatureOptimal
import com.weather.vibe.domain.activityplanner.model.ScoreReason.TemperatureTooHigh
import com.weather.vibe.domain.activityplanner.model.ScoreReason.TemperatureTooLow
import com.weather.vibe.domain.activityplanner.model.ScoreReason.UvHigh
import com.weather.vibe.domain.activityplanner.model.ScoreReason.UvSafe
import com.weather.vibe.domain.activityplanner.model.ScoreReason.WindCalm
import com.weather.vibe.domain.activityplanner.model.ScoreReason.WindStrong
import com.weather.vibe.domain.weather.model.HourlyWeather
import org.koin.core.annotation.Factory

@Factory
class CollectScoreReasons {

  operator fun invoke(
    hour: HourlyWeather,
    uvIndex: Double,
    preferences: ActivityPreferences
  ): List<ScoreReason> = with(preferences) {
    listOf(
      temperatureReason(hour.temperature),
      uvReason(uvIndex),
      windReason(hour.windSpeed),
      precipitationReason(hour.precipitationProbability)
    )
  }

  private fun ActivityPreferences.temperatureReason(celsius: Double): ScoreReason {
    if (celsius in optimalTempRange) return TemperatureOptimal(celsius)
    if (celsius > optimalTempRange.endInclusive) return TemperatureTooHigh(celsius)
    return TemperatureTooLow(celsius)
  }

  private fun ActivityPreferences.uvReason(index: Double): ScoreReason =
    if (index <= maxUvIndex) UvSafe(index) else UvHigh(index)

  private fun ActivityPreferences.windReason(kmh: Double): ScoreReason =
    if (kmh <= maxWindKmh) WindCalm(kmh) else WindStrong(kmh)

  private fun ActivityPreferences.precipitationReason(probability: Int): ScoreReason =
    if (probability <= maxPrecipitationProbability) PrecipitationDry(probability)
    else PrecipitationLikely(probability)
}
