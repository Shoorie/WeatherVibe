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
}
