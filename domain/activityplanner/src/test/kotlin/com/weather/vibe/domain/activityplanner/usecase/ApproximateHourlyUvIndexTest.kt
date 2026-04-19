package com.weather.vibe.domain.activityplanner.usecase

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import java.time.LocalDateTime

class ApproximateHourlyUvIndexTest {

  private val approximateHourlyUvIndex = ApproximateHourlyUvIndex()

  private val sunrise = LocalDateTime.of(2026, 4, 8, 6, 0)
  private val sunset = LocalDateTime.of(2026, 4, 8, 18, 0)
  private val dailyMaxUv = 6.0

  @Test
  fun `given hour before sunrise, then zero uv returned`() {

    val uv = approximateHourlyUvIndex(
      hour = LocalDateTime.of(2026, 4, 8, 4, 0),
      sunrise = sunrise,
      sunset = sunset,
      dailyMaxUvIndex = dailyMaxUv
    )

    expectThat(uv).isEqualTo(0.0)
  }

  @Test
  fun `given hour after sunset, then zero uv returned`() {

    val uv = approximateHourlyUvIndex(
      hour = LocalDateTime.of(2026, 4, 8, 22, 0),
      sunrise = sunrise,
      sunset = sunset,
      dailyMaxUvIndex = dailyMaxUv
    )

    expectThat(uv).isEqualTo(0.0)
  }

  @Test
  fun `given hour at solar noon, then peak uv returned`() {

    val uv = approximateHourlyUvIndex(
      hour = LocalDateTime.of(2026, 4, 8, 12, 0),
      sunrise = sunrise,
      sunset = sunset,
      dailyMaxUvIndex = dailyMaxUv
    )

    expectThat(uv).isEqualTo(dailyMaxUv)
  }

  @Test
  fun `given hour halfway between sunrise and noon, then uv below peak returned`() {

    val uv = approximateHourlyUvIndex(
      hour = LocalDateTime.of(2026, 4, 8, 9, 0),
      sunrise = sunrise,
      sunset = sunset,
      dailyMaxUvIndex = dailyMaxUv
    )

    expectThat(uv).isGreaterThan(0.0).isLessThan(dailyMaxUv)
  }

  @Test
  fun `given null sunrise, then zero uv returned`() {

    val uv = approximateHourlyUvIndex(
      hour = LocalDateTime.of(2026, 4, 8, 12, 0),
      sunrise = null,
      sunset = sunset,
      dailyMaxUvIndex = dailyMaxUv
    )

    expectThat(uv).isEqualTo(0.0)
  }
}
