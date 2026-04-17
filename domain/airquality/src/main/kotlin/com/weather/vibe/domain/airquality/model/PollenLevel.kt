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

    fun from(species: PollenSpecies, grainsPerCubicMetre: Double): PollenLevel = when (species) {
      ALDER, BIRCH -> treePollenLevel(grainsPerCubicMetre)
      GRASS, RAGWEED -> weedPollenLevel(grainsPerCubicMetre)
      MUGWORT -> mugwortPollenLevel(grainsPerCubicMetre)
      OLIVE -> olivePollenLevel(grainsPerCubicMetre)
    }

    private fun treePollenLevel(grains: Double): PollenLevel = when {
      grains < TREE_MODERATE -> LOW
      grains < TREE_HIGH -> MODERATE
      grains < TREE_VERY_HIGH -> HIGH
      else -> VERY_HIGH
    }

    private fun weedPollenLevel(grains: Double): PollenLevel = when {
      grains < WEED_MODERATE -> LOW
      grains < WEED_HIGH -> MODERATE
      grains < WEED_VERY_HIGH -> HIGH
      else -> VERY_HIGH
    }

    private fun mugwortPollenLevel(grains: Double): PollenLevel = when {
      grains < MUGWORT_MODERATE -> LOW
      grains < MUGWORT_HIGH -> MODERATE
      grains < MUGWORT_VERY_HIGH -> HIGH
      else -> VERY_HIGH
    }

    private fun olivePollenLevel(grains: Double): PollenLevel = when {
      grains < OLIVE_MODERATE -> LOW
      grains < OLIVE_HIGH -> MODERATE
      grains < OLIVE_VERY_HIGH -> HIGH
      else -> VERY_HIGH
    }

    private const val TREE_MODERATE = 10.0
    private const val TREE_HIGH = 30.0
    private const val TREE_VERY_HIGH = 100.0

    private const val WEED_MODERATE = 5.0
    private const val WEED_HIGH = 20.0
    private const val WEED_VERY_HIGH = 50.0

    private const val MUGWORT_MODERATE = 10.0
    private const val MUGWORT_HIGH = 30.0
    private const val MUGWORT_VERY_HIGH = 50.0

    private const val OLIVE_MODERATE = 20.0
    private const val OLIVE_HIGH = 200.0
    private const val OLIVE_VERY_HIGH = 400.0
  }
}
