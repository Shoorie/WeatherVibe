package com.weather.vibe.data.location.repository

import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.local.mapper.LocationCacheMapper
import com.weather.vibe.data.location.remote.api.GeocodingApiService
import com.weather.vibe.data.location.remote.mapper.LocationDtoMapper
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [LocationRepository::class])
internal class DefaultLocationRepository(
  private val cacheMapper: LocationCacheMapper,
  private val dao: RecentLocationDao,
  private val dtoMapper: LocationDtoMapper,
  private val service: GeocodingApiService,
) : LocationRepository {

  override suspend fun findById(id: Long): Location? =
    withContext(Dispatchers.IO) {
      dao.findById(id)
        ?.let(cacheMapper::toDomain)
    }

  override suspend fun getRecentLocations(limit: Int): List<Location> =
    withContext(Dispatchers.IO) {
      dao.getRecent(limit)
        .map(cacheMapper::toDomain)
    }

  override fun observeRecentLocations(limit: Int): Flow<List<Location>> =
    dao.observeRecent(limit)
      .map { entities -> entities.map(cacheMapper::toDomain) }
      .flowOn(Dispatchers.IO)

  override suspend fun saveRecentLocation(location: Location) =
    withContext(Dispatchers.IO) {
      dao.insert(cacheMapper.toEntity(location))
    }

  override suspend fun searchLocations(query: String): List<Location> =
    withContext(Dispatchers.IO) {
      service.searchLocations(query).results
        ?.map(dtoMapper::toDomain)
        .orEmpty()
    }
}
