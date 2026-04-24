package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveTodayRating(
  private val repository: RatingRepository,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(): Flow<RatingEntry?> =
    repository.observeForDate(timeProvider.today())
}
