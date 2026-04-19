package com.weather.vibe.domain.activityplanner.model

sealed interface ScoreReason {
  data class TemperatureOptimal(val celsius: Double) : ScoreReason
  data class TemperatureTooHigh(val celsius: Double) : ScoreReason
  data class TemperatureTooLow(val celsius: Double) : ScoreReason
  data class UvSafe(val index: Double) : ScoreReason
  data class UvHigh(val index: Double) : ScoreReason
  data class WindCalm(val kmh: Double) : ScoreReason
  data class WindStrong(val kmh: Double) : ScoreReason
  data class PrecipitationDry(val probability: Int) : ScoreReason
  data class PrecipitationLikely(val probability: Int) : ScoreReason
}
