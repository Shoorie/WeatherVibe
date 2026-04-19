package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.fixture.ScoredHourFixtures.scoredHourAt
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class FindBestWindowsTest {

  private val findBestWindows = FindBestWindows()

  @Test
  fun `given no hours, then empty windows returned`() {

    val windows = findBestWindows(hours = emptyList())

    expectThat(windows).isEmpty()
  }

  @Test
  fun `given only hours below threshold, then empty windows returned`() {

    val hours = (8..12).map { hour -> scoredHourAt(hour = hour, score = 40) }

    val windows = findBestWindows(hours)

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
  fun `given four qualifying windows, then top three returned sorted by average score`() {

    val hours = listOf(
      scoredHourAt(hour = 6, score = 62),
      scoredHourAt(hour = 7, score = 64),
      scoredHourAt(hour = 10, score = 85),
      scoredHourAt(hour = 11, score = 90),
      scoredHourAt(hour = 14, score = 75),
      scoredHourAt(hour = 15, score = 80),
      scoredHourAt(hour = 18, score = 70),
      scoredHourAt(hour = 19, score = 72)
    )

    val windows = findBestWindows(hours)

    expectThat(windows).hasSize(3)
    expectThat(windows.first().averageScore).isEqualTo(87)
  }
}
