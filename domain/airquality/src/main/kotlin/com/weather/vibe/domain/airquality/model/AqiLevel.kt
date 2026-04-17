package com.weather.vibe.domain.airquality.model

enum class AqiLevel {
  GOOD,
  FAIR,
  MODERATE,
  POOR,
  VERY_POOR,
  EXTREMELY_POOR;

  companion object {

    fun from(europeanAqi: Int): AqiLevel = when {
      europeanAqi <= GOOD_UPPER_BOUND -> GOOD
      europeanAqi <= FAIR_UPPER_BOUND -> FAIR
      europeanAqi <= MODERATE_UPPER_BOUND -> MODERATE
      europeanAqi <= POOR_UPPER_BOUND -> POOR
      europeanAqi <= VERY_POOR_UPPER_BOUND -> VERY_POOR
      else -> EXTREMELY_POOR
    }

    private const val GOOD_UPPER_BOUND = 20
    private const val FAIR_UPPER_BOUND = 40
    private const val MODERATE_UPPER_BOUND = 60
    private const val POOR_UPPER_BOUND = 80
    private const val VERY_POOR_UPPER_BOUND = 100
  }
}
