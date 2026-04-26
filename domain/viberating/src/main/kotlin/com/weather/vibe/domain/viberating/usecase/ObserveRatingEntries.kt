package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveRatingEntries(private val repository: RatingRepository) {

  operator fun invoke(): Flow<List<RatingEntry>> =
    repository.observeAll()
}
