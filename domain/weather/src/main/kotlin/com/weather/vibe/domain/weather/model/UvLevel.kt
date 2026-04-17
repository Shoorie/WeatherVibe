package com.weather.vibe.domain.weather.model

enum class UvLevel {
  LOW,
  MODERATE,
  HIGH,
  VERY_HIGH,
  EXTREME;

  companion object {

    fun from(uvIndex: Double): UvLevel = when {
      uvIndex < MODERATE_FLOOR -> LOW
      uvIndex < HIGH_FLOOR -> MODERATE
      uvIndex < VERY_HIGH_FLOOR -> HIGH
      uvIndex < EXTREME_FLOOR -> VERY_HIGH
      else -> EXTREME
    }

    private const val MODERATE_FLOOR = 3.0
    private const val HIGH_FLOOR = 6.0
    private const val VERY_HIGH_FLOOR = 8.0
    private const val EXTREME_FLOOR = 11.0
  }
}
