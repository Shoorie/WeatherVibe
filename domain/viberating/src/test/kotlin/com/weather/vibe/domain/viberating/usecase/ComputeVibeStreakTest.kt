package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures.ratingEntry
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDate
import java.time.LocalDateTime

class ComputeVibeStreakTest {

  private val timeProvider = FakeTimeProvider(current = NOON_TODAY)
  private val computeVibeStreak = ComputeVibeStreak(timeProvider = timeProvider)

  @Test
  fun `when no entries, then streak is zero`() {

    val streak = computeVibeStreak(entries = emptyList())

    expectThat(streak).isEqualTo(0)
  }

  @Test
  fun `when only entry is today, then streak is one`() {

    val streak = computeVibeStreak(entries = listOf(ratingEntry(date = TODAY)))

    expectThat(streak).isEqualTo(1)
  }

  @Test
  fun `when entries cover today and yesterday, then streak is two`() {

    val streak = computeVibeStreak(
      entries = listOf(
        ratingEntry(date = TODAY),
        ratingEntry(date = YESTERDAY)
      )
    )

    expectThat(streak).isEqualTo(2)
  }

  @Test
  fun `when most recent entry is yesterday but today missing, then streak is zero`() {

    val streak = computeVibeStreak(entries = listOf(ratingEntry(date = YESTERDAY)))

    expectThat(streak).isEqualTo(0)
  }

  @Test
  fun `when streak has gap two days back, then streak counts only contiguous head`() {

    val streak = computeVibeStreak(
      entries = listOf(
        ratingEntry(date = TODAY),
        ratingEntry(date = YESTERDAY),
        ratingEntry(date = THREE_DAYS_AGO)
      )
    )

    expectThat(streak).isEqualTo(2)
  }

  @Test
  fun `when same day rated multiple times, then duplicates do not inflate streak`() {

    val streak = computeVibeStreak(
      entries = listOf(
        ratingEntry(id = 1, date = TODAY),
        ratingEntry(id = 2, date = TODAY),
        ratingEntry(id = 3, date = YESTERDAY)
      )
    )

    expectThat(streak).isEqualTo(2)
  }

  private companion object {
    val NOON_TODAY: LocalDateTime = LocalDateTime.of(2026, 4, 26, 12, 0)
    val TODAY: LocalDate = NOON_TODAY.toLocalDate()
    val YESTERDAY: LocalDate = TODAY.minusDays(1)
    val THREE_DAYS_AGO: LocalDate = TODAY.minusDays(3)
  }
}
