package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.fixture.ScoredHourFixtures.scoredHourAt
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

class FindBestWindowsTest {

  private val findBestWindows = FindBestWindows()

  @Test
  fun `given no hours, then empty windows returned`() {

    val windows = findBestWindows(hours = emptyList())

    expectThat(windows).isEmpty()
  }

  @Test
  fun `given single qualifying hour, then one window returned`() {

    val hours = listOf(scoredHourAt(hour = 10, score = 80))

    val windows = findBestWindows(hours)

    expectThat(windows).hasSize(1)
  }

  @Test
  fun `given two consecutive qualifying hours, then single window returned`() {

    val hours = listOf(
      scoredHourAt(hour = 10, score = 80),
      scoredHourAt(hour = 11, score = 85)
    )

    val windows = findBestWindows(hours)

    expectThat(windows).hasSize(1)
  }

  @Test
  fun `given qualifying hours split by poor hour, then two windows returned`() {

    val hours = listOf(
      scoredHourAt(hour = 8, score = 80),
      scoredHourAt(hour = 9, score = 85),
      scoredHourAt(hour = 10, score = 30),
      scoredHourAt(hour = 11, score = 80),
      scoredHourAt(hour = 12, score = 85)
    )

    val windows = findBestWindows(hours)

    expectThat(windows).hasSize(2)
  }

  @Test
  fun `given four qualifying windows, then three top-scoring windows kept`() {

    val windows = findBestWindows(fourWindowsWithDifferentScores())

    expectThat(windows.map { it.start.hour }).containsExactly(6, 10, 18)
  }

  @Test
  fun `given qualifying windows out of chronological order, then windows returned by start time`() {

    val hours = listOf(
      scoredHourAt(hour = 18, score = 80),
      scoredHourAt(hour = 19, score = 80),
      scoredHourAt(hour = 8, score = 90),
      scoredHourAt(hour = 9, score = 90)
    )

    val windows = findBestWindows(hours)

    expectThat(windows.map { it.start.hour }).containsExactly(8, 18)
  }

  @Test
  fun `given hours scoring in fair tier, then no windows returned`() {

    val hours = (8..12).map { hour -> scoredHourAt(hour = hour, score = 60) }

    val windows = findBestWindows(hours)

    expectThat(windows).isEmpty()
  }

  private fun fourWindowsWithDifferentScores() =
    listOf(
      scoredHourAt(hour = 6, score = 75),
      scoredHourAt(hour = 7, score = 76),
      scoredHourAt(hour = 10, score = 85),
      scoredHourAt(hour = 11, score = 90),
      scoredHourAt(hour = 14, score = 72),
      scoredHourAt(hour = 15, score = 73),
      scoredHourAt(hour = 18, score = 88),
      scoredHourAt(hour = 19, score = 89)
    )
}
