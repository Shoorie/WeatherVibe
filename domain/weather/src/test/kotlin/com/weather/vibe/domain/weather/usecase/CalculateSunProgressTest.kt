package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.fake.FakeTimeProvider
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import java.time.LocalDateTime

class CalculateSunProgressTest {

  private val fakeTimeProvider = FakeTimeProvider()
  private val calculateSunProgress = CalculateSunProgress(timeProvider = fakeTimeProvider)

  private val sunrise = LocalDateTime.of(2026, 4, 8, 6, 0)
  private val sunset = LocalDateTime.of(2026, 4, 8, 19, 30)

  @Test
  fun `given now at sunrise, then progress is zero`() {

    fakeTimeProvider.current = sunrise

    val result = calculateSunProgress(sunrise = sunrise, sunset = sunset)

    expectThat(result).isEqualTo(0f)
  }

  @Test
  fun `given now at sunset, then progress is one`() {

    fakeTimeProvider.current = sunset

    val result = calculateSunProgress(sunrise = sunrise, sunset = sunset)

    expectThat(result).isEqualTo(1f)
  }

  @Test
  fun `given now before sunrise, then progress clamps to zero`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 4, 0)

    val result = calculateSunProgress(sunrise = sunrise, sunset = sunset)

    expectThat(result).isEqualTo(0f)
  }

  @Test
  fun `given now after sunset, then progress clamps to one`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 22, 0)

    val result = calculateSunProgress(sunrise = sunrise, sunset = sunset)

    expectThat(result).isEqualTo(1f)
  }

  @Test
  fun `given now at midday, then progress is around half`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 12, 45)

    val result = calculateSunProgress(sunrise = sunrise, sunset = sunset)

    expectThat(result)
      .isGreaterThan(0.4f)
      .isLessThan(0.6f)
  }

  @Test
  fun `given sunset before sunrise, then progress is zero`() {

    fakeTimeProvider.current = sunrise

    val result = calculateSunProgress(sunrise = sunset, sunset = sunrise)

    expectThat(result).isEqualTo(0f)
  }
}
