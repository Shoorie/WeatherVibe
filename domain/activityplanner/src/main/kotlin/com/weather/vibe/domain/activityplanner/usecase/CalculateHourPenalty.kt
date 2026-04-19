package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ActivityPreferences
import com.weather.vibe.domain.weather.model.HourlyWeather
import org.koin.core.annotation.Factory
import kotlin.math.max

@Factory
class CalculateHourPenalty {

  operator fun invoke(
    hour: HourlyWeather,
    uvIndex: Double,
    preferences: ActivityPreferences
  ): Int = with(preferences) {
    temperaturePenalty(hour.temperature) +
      uvPenalty(uvIndex) +
      windPenalty(hour.windSpeed) +
      precipitationPenalty(hour.precipitationProbability)
  }

  private fun ActivityPreferences.temperaturePenalty(celsius: Double): Int {
    if (celsius in optimalTempRange) return NONE
    if (celsius > maxTolerableTemp) return TEMPERATURE_EXTREME
    if (celsius < minTolerableTemp) return TEMPERATURE_EXTREME
    return (distanceFromOptimal(celsius) * TEMPERATURE_PER_DEGREE).toInt()
  }

  private fun ActivityPreferences.uvPenalty(index: Double): Int =
    (max(0.0, index - maxUvIndex) * UV_PER_UNIT).toInt()

  private fun ActivityPreferences.windPenalty(kmh: Double): Int =
    (max(0.0, kmh - maxWindKmh) * WIND_PER_KMH).toInt()

  private fun ActivityPreferences.precipitationPenalty(probability: Int): Int =
    (max(0, probability - maxPrecipitationProbability) * PRECIP_PER_PERCENT).toInt()

  private fun ActivityPreferences.distanceFromOptimal(celsius: Double): Double =
    if (celsius < optimalTempRange.start) optimalTempRange.start - celsius
    else celsius - optimalTempRange.endInclusive

  private companion object {
    const val NONE = 0
    const val TEMPERATURE_EXTREME = 40
    const val TEMPERATURE_PER_DEGREE = 3.0
    const val UV_PER_UNIT = 6.0
    const val WIND_PER_KMH = 1.5
    const val PRECIP_PER_PERCENT = 0.8
  }
}
