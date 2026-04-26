package com.weather.vibe.domain.viberating.repository

import com.weather.vibe.domain.viberating.model.RatingEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RatingRepository {

  fun observeAll(): Flow<List<RatingEntry>>

  fun observeForDate(date: LocalDate): Flow<List<RatingEntry>>

  suspend fun insert(entry: RatingEntry)
}
