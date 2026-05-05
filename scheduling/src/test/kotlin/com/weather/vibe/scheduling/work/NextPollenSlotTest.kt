package com.weather.vibe.scheduling.work

import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.Duration
import java.time.LocalDateTime

class NextPollenSlotTest {

  @Test
  fun `when before morning slot, then schedule for 7am same day`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 5, 30))

    val delay = nextPollenDelay(timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofHours(1).plusMinutes(30))
  }

  @Test
  fun `when between morning and noon slot, then schedule for 1pm same day`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 9, 0))

    val delay = nextPollenDelay(timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofHours(4))
  }

  @Test
  fun `when between noon and evening slot, then schedule for 7pm same day`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 15, 0))

    val delay = nextPollenDelay(timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofHours(4))
  }

  @Test
  fun `when after evening slot, then skip night and schedule for 7am next day`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 23, 0))

    val delay = nextPollenDelay(timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofHours(8))
  }

  @Test
  fun `when in middle of night, then schedule for 7am same day`() {

    val timeProvider = FakeTimeProvider(current = LocalDateTime.of(2026, 4, 8, 2, 0))

    val delay = nextPollenDelay(timeProvider = timeProvider)

    expectThat(delay).isEqualTo(Duration.ofHours(5))
  }
}
