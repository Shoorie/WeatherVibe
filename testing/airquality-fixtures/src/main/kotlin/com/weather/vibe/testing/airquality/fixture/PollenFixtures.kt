package com.weather.vibe.testing.airquality.fixture

import com.weather.vibe.domain.airquality.model.Pollen
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.airquality.model.PollenLevel.LOW
import com.weather.vibe.domain.airquality.model.PollenReading
import com.weather.vibe.domain.airquality.model.PollenSpecies
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.airquality.model.PollenSpecies.GRASS
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.COORDINATES
import java.time.LocalDateTime

object PollenFixtures {

  const val LOW_GRAINS = 2.0
  const val MODERATE_GRAINS = 15.0
  const val HIGH_GRAINS = 40.0
  const val VERY_HIGH_GRAINS = 120.0
  val MEASURED_AT: LocalDateTime = LocalDateTime.of(2026, 4, 16, 15, 0)

  val CALM: Pollen = pollen(
    readings = listOf(
      pollenReading(species = BIRCH, grainsPerCubicMetre = LOW_GRAINS, level = LOW),
      pollenReading(species = GRASS, grainsPerCubicMetre = LOW_GRAINS, level = LOW)
    )
  )

  val HIGH_BIRCH: Pollen = pollen(
    readings = listOf(
      pollenReading(species = BIRCH, grainsPerCubicMetre = HIGH_GRAINS, level = HIGH)
    )
  )

  fun pollen(
    coordinates: Coordinates = COORDINATES,
    measuredAt: LocalDateTime = MEASURED_AT,
    readings: List<PollenReading> = emptyList()
  ): Pollen = Pollen(
    coordinates = coordinates,
    measuredAt = measuredAt,
    readings = readings
  )

  fun pollenReading(
    species: PollenSpecies = BIRCH,
    grainsPerCubicMetre: Double = HIGH_GRAINS,
    level: PollenLevel = HIGH
  ): PollenReading = PollenReading(
    species = species,
    grainsPerCubicMetre = grainsPerCubicMetre,
    level = level
  )
}
