package com.weather.vibe.domain.viberating.model

import java.time.LocalDate

data class RatingEntry(
  val date: LocalDate,
  val rating: Int,
  val note: String,
  val weather: WeatherSnapshot,
  val createdAtEpochMs: Long
) {

  init {
    require(rating in RATING_MIN..RATING_MAX) { "rating must be in $RATING_MIN..$RATING_MAX, got $rating" }
    require(note.length <= NOTE_MAX_LENGTH) { "note exceeds $NOTE_MAX_LENGTH characters" }
  }

  companion object {
    const val RATING_MIN: Int = 1
    const val RATING_MAX: Int = 5
    const val NOTE_MAX_LENGTH: Int = 80
  }
}
