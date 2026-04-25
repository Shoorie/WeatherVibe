package com.weather.vibe.domain.viberating.model

import java.time.LocalDate

data class RatingEntry(
  val id: Long = 0,
  val date: LocalDate,
  val rating: Int,
  val weather: WeatherSnapshot,
  val createdAtEpochMs: Long,
  val note: String? = null
) {

  init {
    require(rating in RATING_MIN..RATING_MAX) {
      "rating must be in $RATING_MIN..$RATING_MAX, got $rating"
    }
    require(note == null || note.length <= NOTE_MAX_LENGTH) {
      "note must be at most $NOTE_MAX_LENGTH characters, got ${note?.length}"
    }
  }

  companion object {
    const val RATING_MIN: Int = 1
    const val RATING_MAX: Int = 5
    const val NOTE_MAX_LENGTH: Int = 140
  }
}
