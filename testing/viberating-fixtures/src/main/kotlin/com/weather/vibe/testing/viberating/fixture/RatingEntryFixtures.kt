package com.weather.vibe.testing.viberating.fixture

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import java.time.LocalDate

object RatingEntryFixtures {

  const val DEFAULT_CREATED_AT_EPOCH_MS: Long = 1_745_500_000_000L
  const val ONE_HOUR_MS: Long = 60 * 60 * 1_000L

  val DEFAULT_DATE: LocalDate = LocalDate.of(2026, 4, 24)

  fun ratingEntry(
    id: Long = 0,
    date: LocalDate = DEFAULT_DATE,
    rating: Int = 4,
    weather: WeatherSnapshot = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C,
    createdAtEpochMs: Long = DEFAULT_CREATED_AT_EPOCH_MS,
    note: String? = null
  ): RatingEntry = RatingEntry(
    id = id,
    date = date,
    rating = rating,
    weather = weather,
    createdAtEpochMs = createdAtEpochMs,
    note = note
  )

  fun sampleWeek(startDate: LocalDate = DEFAULT_DATE.minusDays(6)): List<RatingEntry> =
    listOf(
      ratingEntry(id = 1, date = startDate, rating = 3, weather = WeatherSnapshotFixtures.RAIN_12C),
      ratingEntry(id = 2, date = startDate.plusDays(1), rating = 4, weather = WeatherSnapshotFixtures.CLOUDY_14C),
      ratingEntry(id = 3, date = startDate.plusDays(2), rating = 5, weather = WeatherSnapshotFixtures.SUNNY_20C),
      ratingEntry(id = 4, date = startDate.plusDays(3), rating = 4, weather = WeatherSnapshotFixtures.SUNNY_20C),
      ratingEntry(id = 5, date = startDate.plusDays(4), rating = 2, weather = WeatherSnapshotFixtures.RAIN_12C),
      ratingEntry(id = 6, date = startDate.plusDays(5), rating = 3, weather = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C),
      ratingEntry(id = 7, date = startDate.plusDays(6), rating = 5, weather = WeatherSnapshotFixtures.SUNNY_20C)
    )
}
