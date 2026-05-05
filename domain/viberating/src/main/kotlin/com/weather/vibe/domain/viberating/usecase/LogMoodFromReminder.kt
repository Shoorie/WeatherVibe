package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.RatingEntry.Companion.RATING_MAX
import com.weather.vibe.domain.viberating.model.RatingEntry.Companion.RATING_MIN
import org.koin.core.annotation.Factory

@Factory
class LogMoodFromReminder internal constructor(
  private val captureWeatherSnapshot: CaptureWeatherSnapshot,
  private val saveRatingEntry: SaveRatingEntry,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(rating: Int) {

    val coerced = rating.coerceIn(RATING_MIN, RATING_MAX)
    val now = timeProvider.now()
    val entry = RatingEntry(
      createdAtEpochMs = timeProvider.nowEpochMillis(),
      date = now.toLocalDate(),
      rating = coerced,
      weather = captureWeatherSnapshot()
    )
    saveRatingEntry(entry)
  }
}
