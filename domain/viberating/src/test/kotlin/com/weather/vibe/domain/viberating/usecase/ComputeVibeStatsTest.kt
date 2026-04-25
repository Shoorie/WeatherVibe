package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.weather.model.Condition.CLOUDY
import com.weather.vibe.domain.weather.model.Condition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.Condition.RAIN
import com.weather.vibe.domain.weather.model.Condition.SUNNY
import com.weather.vibe.domain.viberating.model.VibeStats
import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures
import com.weather.vibe.testing.viberating.fixture.RatingEntryFixtures.ratingEntry
import com.weather.vibe.testing.viberating.fixture.WeatherSnapshotFixtures
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class ComputeVibeStatsTest {

  private val computeVibeStats = ComputeVibeStats()

  @Test
  fun `when there are no ratings, then statistics are empty`() {

    val stats = computeVibeStats(entries = emptyList())

    expectThat(stats).isEqualTo(VibeStats.EMPTY)
  }

  @Test
  fun `when there are no ratings, then favorite weather is unknown`() {

    val stats = computeVibeStats(entries = emptyList())

    expectThat(stats.favoriteCondition).isNull()
  }

  @Test
  fun `given a single rating, then the average matches that rating`() {

    val stats = computeVibeStats(
      entries = listOf(ratingEntry(rating = 4, weather = WeatherSnapshotFixtures.SUNNY_20C))
    )

    expectThat(stats.averageRating).isEqualTo(4.0)
  }

  @Test
  fun `given a single rating, then the favorite weather is the one rated`() {

    val stats = computeVibeStats(
      entries = listOf(ratingEntry(rating = 4, weather = WeatherSnapshotFixtures.SUNNY_20C))
    )

    expectThat(stats.favoriteCondition).isEqualTo(SUNNY)
  }

  @Test
  fun `given several weather types are rated, then weather is ranked from best to worst`() {

    val stats = computeVibeStats(
      entries = listOf(
        ratingEntry(rating = 5, weather = WeatherSnapshotFixtures.SUNNY_20C),
        ratingEntry(rating = 4, weather = WeatherSnapshotFixtures.SUNNY_20C),
        ratingEntry(rating = 2, weather = WeatherSnapshotFixtures.RAIN_12C),
        ratingEntry(rating = 3, weather = WeatherSnapshotFixtures.CLOUDY_14C)
      )
    )

    expectThat(stats.conditionAverages.map { it.condition })
      .containsExactly(SUNNY, CLOUDY, RAIN)
  }

  @Test
  fun `given several weather types are rated, then favorite weather has the highest average`() {

    val stats = computeVibeStats(
      entries = listOf(
        ratingEntry(rating = 2, weather = WeatherSnapshotFixtures.SUNNY_20C),
        ratingEntry(rating = 5, weather = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C)
      )
    )

    expectThat(stats.favoriteCondition).isEqualTo(PARTLY_CLOUDY)
  }

  @Test
  fun `given the same weather is rated several times, then those ratings are averaged together`() {

    val stats = computeVibeStats(
      entries = listOf(
        ratingEntry(rating = 4, weather = WeatherSnapshotFixtures.RAIN_12C),
        ratingEntry(rating = 2, weather = WeatherSnapshotFixtures.RAIN_12C)
      )
    )

    val rainBucket = stats.conditionAverages.single { it.condition == RAIN }
    expectThat(rainBucket.averageRating).isEqualTo(3.0)
  }

  @Test
  fun `given several ratings on the same day, then the day count is one`() {

    val today = RatingEntryFixtures.DEFAULT_DATE
    val stats = computeVibeStats(
      entries = listOf(
        ratingEntry(date = today, rating = 4, createdAtEpochMs = 1L),
        ratingEntry(date = today, rating = 2, createdAtEpochMs = 2L),
        ratingEntry(date = today, rating = 5, createdAtEpochMs = 3L)
      )
    )

    expectThat(stats.uniqueDayCount).isEqualTo(1)
  }

  @Test
  fun `given several ratings on the same day, then every entry is counted in the total`() {

    val today = RatingEntryFixtures.DEFAULT_DATE
    val stats = computeVibeStats(
      entries = listOf(
        ratingEntry(date = today, rating = 4, createdAtEpochMs = 1L),
        ratingEntry(date = today, rating = 2, createdAtEpochMs = 2L),
        ratingEntry(date = today, rating = 5, createdAtEpochMs = 3L)
      )
    )

    expectThat(stats.totalEntries).isEqualTo(3)
  }

  @Test
  fun `given ratings span several days, then the day count matches the number of distinct days`() {

    val day = RatingEntryFixtures.DEFAULT_DATE
    val stats = computeVibeStats(
      entries = listOf(
        ratingEntry(date = day, rating = 4),
        ratingEntry(date = day.plusDays(1), rating = 3),
        ratingEntry(date = day.plusDays(1), rating = 5),
        ratingEntry(date = day.plusDays(2), rating = 2)
      )
    )

    expectThat(stats.uniqueDayCount).isEqualTo(3)
  }
}
