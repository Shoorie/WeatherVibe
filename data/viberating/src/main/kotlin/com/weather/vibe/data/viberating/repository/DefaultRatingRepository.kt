package com.weather.vibe.data.viberating.repository

import com.weather.vibe.data.viberating.local.dao.RatingDao
import com.weather.vibe.data.viberating.local.mapper.toDomain
import com.weather.vibe.data.viberating.local.mapper.toEntity
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import java.time.LocalDate

@Single(binds = [RatingRepository::class])
internal class DefaultRatingRepository(
  private val dao: RatingDao
) : RatingRepository {

  override fun observeAll(): Flow<List<RatingEntry>> =
    dao.observeAll()
      .map { entities -> entities.map { it.toDomain() } }
      .flowOn(Dispatchers.IO)

  override fun observeForDate(date: LocalDate): Flow<RatingEntry?> =
    dao.observeForDate(date)
      .map { it?.toDomain() }
      .flowOn(Dispatchers.IO)

  override suspend fun upsert(entry: RatingEntry) {
    dao.upsert(entry.toEntity())
  }
}
