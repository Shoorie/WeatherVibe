package com.weather.vibe.domain.airquality.model

import com.weather.vibe.domain.weather.model.Coordinates
import java.time.LocalDateTime

data class AirQuality(
  val coordinates: Coordinates,
  val europeanAqi: Int,
  val measuredAt: LocalDateTime
) {

  val level: AqiLevel =
    AqiLevel.from(europeanAqi)
}
