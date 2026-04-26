package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPreferences
import com.weather.vibe.domain.activityplanner.model.ScoreReason
import com.weather.vibe.domain.activityplanner.model.ScoreReason.ConditionBlocking
import com.weather.vibe.domain.activityplanner.model.ScoreReason.ConditionFriendly
import com.weather.vibe.domain.activityplanner.model.ScoreReason.Daylight
import com.weather.vibe.domain.activityplanner.model.ScoreReason.GustsCalm
import com.weather.vibe.domain.activityplanner.model.ScoreReason.GustsStrong
import com.weather.vibe.domain.activityplanner.model.ScoreReason.NightHours
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
import com.weather.vibe.domain.weather.model.WeatherCondition
import org.koin.core.annotation.Factory
import java.time.LocalDateTime

@Factory
class CollectScoreReasons {

  operator fun invoke(
    hour: HourlyWeather,
    uvIndex: Double,
    sunrise: LocalDateTime?,
    sunset: LocalDateTime?,
    preferences: ActivityPreferences
  ): List<ScoreReason> = with(preferences) {
    listOf(
      conditionReason(hour.condition),
      temperatureReason(hour.apparentTemperature),
      uvReason(uvIndex),
      windReason(hour.windSpeed),
      gustsReason(hour.windGusts),
      precipitationReason(hour.precipitationProbability),
      daylightReason(hour.time, sunrise, sunset)
    )
  }

  private fun ActivityPreferences.conditionReason(condition: WeatherCondition): ScoreReason =
    if (condition in blockedConditions) ConditionBlocking(condition)
    else ConditionFriendly(condition)

  private fun ActivityPreferences.temperatureReason(celsius: Double): ScoreReason {
    if (celsius in optimalTempRange) return TemperatureOptimal(celsius)
    if (celsius > optimalTempRange.endInclusive) return TemperatureTooHigh(celsius)
    return TemperatureTooLow(celsius)
  }

  private fun ActivityPreferences.uvReason(index: Double): ScoreReason =
    if (index <= maxUvIndex) UvSafe(index) else UvHigh(index)

  private fun ActivityPreferences.windReason(kmh: Double): ScoreReason =
    if (kmh <= maxWindKmh) WindCalm(kmh) else WindStrong(kmh)

  private fun ActivityPreferences.gustsReason(kmh: Double): ScoreReason =
    if (kmh <= maxGustsKmh) GustsCalm(kmh) else GustsStrong(kmh)

  private fun ActivityPreferences.precipitationReason(probability: Int): ScoreReason =
    if (probability <= maxPrecipitationProbability) PrecipitationDry(probability)
    else PrecipitationLikely(probability)

  private fun daylightReason(
    hour: LocalDateTime,
    sunrise: LocalDateTime?,
    sunset: LocalDateTime?
  ): ScoreReason = when {
    sunrise == null || sunset == null -> Daylight
    hour.isBefore(sunrise) || hour.isAfter(sunset) -> NightHours
    else -> Daylight
  }
}
