package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import java.time.LocalDate
import java.time.LocalDateTime

class IsDateTodayTest {

  private val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 13, 10, 0))
  private val isDateToday = IsDateToday(timeProvider)

  @Test
  fun `given today's date, then true returned`() {

    expectThat(isDateToday(LocalDate.of(2026, 4, 13))).isTrue()
  }

  @Test
  fun `given tomorrow's date, then false returned`() {

    expectThat(isDateToday(LocalDate.of(2026, 4, 14))).isFalse()
  }

  @Test
  fun `given yesterday's date, then false returned`() {

    expectThat(isDateToday(LocalDate.of(2026, 4, 12))).isFalse()
  }
}
