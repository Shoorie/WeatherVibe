package com.weather.vibe.scheduling.work

import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class DelayUntilNextTest {

  @Test
  fun `when target is later today, then return delay until target today`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 6, 0))

    val delay = delayUntilNext(target = LocalTime.of(7, 30), timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofMinutes(90))
  }

  @Test
  fun `when target already passed today, then return delay until tomorrow`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 8, 0))

    val delay = delayUntilNext(target = LocalTime.of(7, 30), timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofHours(23).plusMinutes(30))
  }

  @Test
  fun `when now equals target, then return delay until tomorrow`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 7, 30))

    val delay = delayUntilNext(target = LocalTime.of(7, 30), timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofDays(1))
  }
}
