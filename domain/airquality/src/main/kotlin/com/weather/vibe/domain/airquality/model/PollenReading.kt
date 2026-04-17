package com.weather.vibe.domain.airquality.model

data class PollenReading(
  val species: PollenSpecies,
  val grainsPerCubicMetre: Double,
  val level: PollenLevel
)
