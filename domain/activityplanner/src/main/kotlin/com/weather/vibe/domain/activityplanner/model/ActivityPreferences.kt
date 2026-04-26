package com.weather.vibe.domain.activityplanner.model

import com.weather.vibe.domain.weather.model.WeatherCondition

data class ActivityPreferences(
  val blockedConditions: Set<WeatherCondition>,
  val daylightPenalty: Int,
  val maxGustsKmh: Double,
  val maxPrecipitationProbability: Int,
  val maxTolerableTemp: Double,
  val maxUvIndex: Double,
  val maxWindKmh: Double,
  val minTolerableTemp: Double,
  val optimalTempRange: ClosedFloatingPointRange<Double>
) {

  companion object {

    private val DAYTIME_HAZARDS = setOf(
      WeatherCondition.THUNDERSTORM,
      WeatherCondition.FREEZING_RAIN,
      WeatherCondition.FREEZING_DRIZZLE
    )

    private val CYCLING_HAZARDS = DAYTIME_HAZARDS + WeatherCondition.FOG

    private val RUNNING = ActivityPreferences(
      blockedConditions = DAYTIME_HAZARDS,
      daylightPenalty = 50,
      maxGustsKmh = 35.0,
      maxPrecipitationProbability = 30,
      maxTolerableTemp = 26.0,
      maxUvIndex = 5.0,
      maxWindKmh = 25.0,
      minTolerableTemp = -5.0,
      optimalTempRange = 10.0..18.0
    )

    private val WALKING = ActivityPreferences(
      blockedConditions = setOf(
        WeatherCondition.THUNDERSTORM,
        WeatherCondition.FREEZING_RAIN
      ),
      daylightPenalty = 25,
      maxGustsKmh = 50.0,
      maxPrecipitationProbability = 40,
      maxTolerableTemp = 30.0,
      maxUvIndex = 6.0,
      maxWindKmh = 35.0,
      minTolerableTemp = -10.0,
      optimalTempRange = 12.0..22.0
    )

    private val CYCLING = ActivityPreferences(
      blockedConditions = CYCLING_HAZARDS,
      daylightPenalty = 60,
      maxGustsKmh = 30.0,
      maxPrecipitationProbability = 25,
      maxTolerableTemp = 28.0,
      maxUvIndex = 5.0,
      maxWindKmh = 20.0,
      minTolerableTemp = 0.0,
      optimalTempRange = 14.0..22.0
    )

    fun forActivity(type: ActivityType): ActivityPreferences =
      when (type) {
        ActivityType.RUNNING -> RUNNING
        ActivityType.WALKING -> WALKING
        ActivityType.CYCLING -> CYCLING
      }
  }
}
