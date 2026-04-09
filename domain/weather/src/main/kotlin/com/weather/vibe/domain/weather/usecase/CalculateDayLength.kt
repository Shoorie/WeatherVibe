package com.weather.vibe.domain.weather.usecase

import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDateTime

@Factory
class CalculateDayLength {

  operator fun invoke(
    sunrise: LocalDateTime,
    sunset: LocalDateTime
  ): Duration =
    Duration.between(sunrise, sunset)
}
