package com.weather.vibe.domain.vibe.model

enum class VibeMood {
  ROUGH,
  DREARY,
  OKAY,
  PLEASANT,
  RADIANT;

  companion object {

    fun from(score: Int): VibeMood = when {
      score >= RADIANT_FLOOR -> RADIANT
      score >= PLEASANT_FLOOR -> PLEASANT
      score >= OKAY_FLOOR -> OKAY
      score >= DREARY_FLOOR -> DREARY
      else -> ROUGH
    }

    private const val RADIANT_FLOOR = 85
    private const val PLEASANT_FLOOR = 65
    private const val OKAY_FLOOR = 45
    private const val DREARY_FLOOR = 25
  }
}
