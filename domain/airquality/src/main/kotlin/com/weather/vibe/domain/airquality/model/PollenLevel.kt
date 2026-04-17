package com.weather.vibe.domain.airquality.model

import com.weather.vibe.domain.airquality.model.PollenSpecies.ALDER
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.airquality.model.PollenSpecies.GRASS
import com.weather.vibe.domain.airquality.model.PollenSpecies.MUGWORT
import com.weather.vibe.domain.airquality.model.PollenSpecies.OLIVE
import com.weather.vibe.domain.airquality.model.PollenSpecies.RAGWEED

enum class PollenLevel {
  LOW,
  MODERATE,
  HIGH,
  VERY_HIGH;

  companion object {

    fun from(
      species: PollenSpecies,
      grainsPerCubicMetre: Double
    ): PollenLevel = when (species) {
      ALDER, BIRCH -> treePollenLevel(grainsPerCubicMetre)
      GRASS, RAGWEED -> weedPollenLevel(grainsPerCubicMetre)
      MUGWORT -> mugwortPollenLevel(grainsPerCubicMetre)
      OLIVE -> olivePollenLevel(grainsPerCubicMetre)
    }

    private fun treePollenLevel(grains: Double): PollenLevel =
      when {
        grains < 10.0 -> LOW
        grains < 30.0 -> MODERATE
        grains < 100.0 -> HIGH
        else -> VERY_HIGH
      }

    private fun weedPollenLevel(grains: Double): PollenLevel =
      when {
        grains < 5.0 -> LOW
        grains < 20.0 -> MODERATE
        grains < 50.0 -> HIGH
        else -> VERY_HIGH
      }

    private fun mugwortPollenLevel(grains: Double): PollenLevel =
      when {
        grains < 10.0 -> LOW
        grains < 30.0 -> MODERATE
        grains < 50.0 -> HIGH
        else -> VERY_HIGH
      }

    private fun olivePollenLevel(grains: Double): PollenLevel =
      when {
        grains < 20.0 -> LOW
        grains < 200.0 -> MODERATE
        grains < 400.0 -> HIGH
        else -> VERY_HIGH
      }
  }
}
