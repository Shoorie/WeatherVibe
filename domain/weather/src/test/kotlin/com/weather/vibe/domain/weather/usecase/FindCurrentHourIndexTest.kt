package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

class FindCurrentHourIndexTest {

  private val fakeTimeProvider = FakeTimeProvider()
  private val findCurrentHourIndex = FindCurrentHourIndex(timeProvider = fakeTimeProvider)

  private val hours = listOf(
    LocalDateTime.of(2026, 4, 8, 12, 0),
    LocalDateTime.of(2026, 4, 8, 13, 0),
    LocalDateTime.of(2026, 4, 8, 14, 0),
    LocalDateTime.of(2026, 4, 8, 15, 0)
  )

  @Test
  fun `given now matches first hour, when searched, then return zero`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 12, 0)

    val result = findCurrentHourIndex(hours = hours)

    expectThat(result).isEqualTo(0)
  }

  @Test
  fun `given now within middle hour, when searched, then return index of that hour`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 13, 45)

    val result = findCurrentHourIndex(hours = hours)

    expectThat(result).isEqualTo(1)
  }

  @Test
  fun `given now matches last hour exactly, when searched, then return its index`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 15, 0)

    val result = findCurrentHourIndex(hours = hours)

    expectThat(result).isEqualTo(3)
  }

  @Test
  fun `given now before all hours, when searched, then return not found`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 11, 0)

    val result = findCurrentHourIndex(hours = hours)

    expectThat(result).isEqualTo(-1)
  }

  @Test
  fun `given now after all hours, when searched, then return not found`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 20, 0)

    val result = findCurrentHourIndex(hours = hours)

    expectThat(result).isEqualTo(-1)
  }

  @Test
  fun `given empty hour list, when searched, then return not found`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 12, 0)

    val result = findCurrentHourIndex(hours = emptyList())

    expectThat(result).isEqualTo(-1)
  }
}
