package com.weather.vibe.domain.activityplanner.model

data class ActivityPreferences(
  val optimalTempRange: ClosedFloatingPointRange<Double>,
  val maxTolerableTemp: Double,
  val minTolerableTemp: Double,
  val maxUvIndex: Double,
  val maxWindKmh: Double,
  val maxPrecipitationProbability: Int
) {

  companion object {

    private val RUNNING = ActivityPreferences(
      optimalTempRange = 10.0..18.0,
      maxTolerableTemp = 26.0,
      minTolerableTemp = -5.0,
      maxUvIndex = 5.0,
      maxWindKmh = 25.0,
      maxPrecipitationProbability = 30
    )

    private val WALKING = ActivityPreferences(
      optimalTempRange = 12.0..22.0,
      maxTolerableTemp = 30.0,
      minTolerableTemp = -10.0,
      maxUvIndex = 6.0,
      maxWindKmh = 35.0,
      maxPrecipitationProbability = 40
    )

    private val CYCLING = ActivityPreferences(
      optimalTempRange = 14.0..22.0,
      maxTolerableTemp = 28.0,
      minTolerableTemp = 0.0,
      maxUvIndex = 5.0,
      maxWindKmh = 20.0,
      maxPrecipitationProbability = 25
    )

    fun forActivity(type: ActivityType): ActivityPreferences =
      when (type) {
        ActivityType.RUNNING -> RUNNING
        ActivityType.WALKING -> WALKING
        ActivityType.CYCLING -> CYCLING
      }
  }
}
