package com.weather.vibe.testing.viberating.fixture

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import java.time.LocalDate

object RatingEntryFixtures {

  const val DEFAULT_CREATED_AT_EPOCH_MS: Long = 1_745_500_000_000L

  val DEFAULT_DATE: LocalDate = LocalDate.of(2026, 4, 24)

  fun ratingEntry(
    id: Long = 0,
    date: LocalDate = DEFAULT_DATE,
    rating: Int = 4,
    note: String = "",
    weather: WeatherSnapshot = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C,
    createdAtEpochMs: Long = DEFAULT_CREATED_AT_EPOCH_MS
  ): RatingEntry = RatingEntry(
    id = id,
    date = date,
    rating = rating,
    note = note,
    weather = weather,
    createdAtEpochMs = createdAtEpochMs
  )

  fun sampleWeek(startDate: LocalDate = DEFAULT_DATE.minusDays(6)): List<RatingEntry> =
    listOf(
      ratingEntry(date = startDate, rating = 3, weather = WeatherSnapshotFixtures.RAIN_12C),
      ratingEntry(date = startDate.plusDays(1), rating = 4, weather = WeatherSnapshotFixtures.CLOUDY_14C),
      ratingEntry(date = startDate.plusDays(2), rating = 5, weather = WeatherSnapshotFixtures.SUNNY_20C),
      ratingEntry(date = startDate.plusDays(3), rating = 4, weather = WeatherSnapshotFixtures.SUNNY_20C),
      ratingEntry(date = startDate.plusDays(4), rating = 2, weather = WeatherSnapshotFixtures.RAIN_12C),
      ratingEntry(date = startDate.plusDays(5), rating = 3, weather = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C),
      ratingEntry(date = startDate.plusDays(6), rating = 5, weather = WeatherSnapshotFixtures.SUNNY_20C)
    )
}
