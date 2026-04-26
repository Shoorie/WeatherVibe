package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import org.koin.core.annotation.Factory

@Factory
class ComputeVibeStreak(
  private val timeProvider: TimeProvider
) {

  operator fun invoke(entries: List<RatingEntry>): Int {

    val ratedDates = entries.mapTo(
      destination = mutableSetOf(),
      transform = RatingEntry::date
    )

    return generateSequence(timeProvider.today()) { it.minusDays(ONE_DAY) }
      .takeWhile { it in ratedDates }
      .count()
  }

  private companion object {
    const val ONE_DAY = 1L
  }
}
