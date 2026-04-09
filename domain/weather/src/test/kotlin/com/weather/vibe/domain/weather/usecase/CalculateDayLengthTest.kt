package com.weather.vibe.domain.weather.usecase

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.Duration
import java.time.LocalDateTime

class CalculateDayLengthTest {

  private val calculateDayLength = CalculateDayLength()

  @Test
  fun `when called with typical day, then return duration between sunrise and sunset`() {

    val sunrise = LocalDateTime.of(2026, 4, 8, 6, 0)
    val sunset = LocalDateTime.of(2026, 4, 8, 19, 30)

    val result = calculateDayLength(sunrise = sunrise, sunset = sunset)

    expectThat(result).isEqualTo(Duration.ofHours(13).plusMinutes(30))
  }

  @Test
  fun `given short winter day, when called, then return shorter duration`() {

    val sunrise = LocalDateTime.of(2026, 12, 21, 8, 30)
    val sunset = LocalDateTime.of(2026, 12, 21, 15, 45)

    val result = calculateDayLength(sunrise = sunrise, sunset = sunset)

    expectThat(result).isEqualTo(Duration.ofHours(7).plusMinutes(15))
  }

  @Test
  fun `given sunset equals sunrise, when called, then return zero`() {

    val moment = LocalDateTime.of(2026, 4, 8, 12, 0)

    val result = calculateDayLength(sunrise = moment, sunset = moment)

    expectThat(result).isEqualTo(Duration.ZERO)
  }
}
