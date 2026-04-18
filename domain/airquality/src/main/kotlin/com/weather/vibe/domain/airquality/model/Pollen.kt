package com.weather.vibe.domain.airquality.model

import com.weather.vibe.domain.weather.model.Coordinates
import java.time.LocalDateTime

data class Pollen(
  val coordinates: Coordinates,
  val measuredAt: LocalDateTime,
  val readings: List<PollenReading>
) {

  val notableReading: PollenReading? =
    readings
      .filter { it.level.ordinal >= NOTABLE_THRESHOLD.ordinal }
      .maxByOrNull { it.level.ordinal }

  private companion object {
    val NOTABLE_THRESHOLD: PollenLevel = PollenLevel.MODERATE
  }
}
