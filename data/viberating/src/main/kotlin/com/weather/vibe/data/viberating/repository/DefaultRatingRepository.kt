package com.weather.vibe.data.viberating.repository

import com.weather.vibe.data.viberating.local.dao.RatingDao
import com.weather.vibe.data.viberating.local.mapper.toDomain
import com.weather.vibe.data.viberating.local.mapper.toEntity
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.repository.RatingRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.time.LocalDate

@Single(binds = [RatingRepository::class])
internal class DefaultRatingRepository(
  private val dao: RatingDao
) : RatingRepository {

  override fun observeAll(): Flow<List<RatingEntry>> =
    dao.observeAll()
      .map { entities -> entities.map { it.toDomain() } }
      .flowOn(IO)

  override fun observeForDate(date: LocalDate): Flow<List<RatingEntry>> =
    dao.observeForDate(date)
      .map { entities -> entities.map { it.toDomain() } }
      .flowOn(IO)

  override suspend fun insert(entry: RatingEntry) {
    withContext(IO) {
      dao.insert(entry.toEntity())
    }
  }
}
