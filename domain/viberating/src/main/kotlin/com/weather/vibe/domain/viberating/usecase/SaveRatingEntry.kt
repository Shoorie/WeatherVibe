package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import org.koin.core.annotation.Factory

@Factory
class SaveRatingEntry(private val repository: RatingRepository) {

  suspend operator fun invoke(entry: RatingEntry) {
    repository.upsert(entry)
  }
}
