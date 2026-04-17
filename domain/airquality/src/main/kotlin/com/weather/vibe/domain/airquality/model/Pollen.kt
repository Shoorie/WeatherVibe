package com.weather.vibe.domain.airquality.model

import com.weather.vibe.domain.weather.model.Coordinates
import java.time.LocalDateTime

data class Pollen(
  val coordinates: Coordinates,
  val measuredAt: LocalDateTime,
  val readings: List<PollenReading>
)
