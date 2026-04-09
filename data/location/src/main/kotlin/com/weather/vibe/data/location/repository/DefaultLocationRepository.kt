package com.weather.vibe.data.location.repository

import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.mapper.LocationCacheMapper
import com.weather.vibe.data.location.mapper.LocationDtoMapper
import com.weather.vibe.data.location.remote.api.GeocodingApiService
import com.weather.vibe.domain.location.model.LocationResult
import com.weather.vibe.domain.location.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [LocationRepository::class])
internal class DefaultLocationRepository(
  private val cacheMapper: LocationCacheMapper,
  private val dtoMapper: LocationDtoMapper,
  private val geocodingApiService: GeocodingApiService,
  private val recentLocationDao: RecentLocationDao
) : LocationRepository {

  override suspend fun getRecentLocations(limit: Int): List<LocationResult> =
    withContext(Dispatchers.IO) {
      recentLocationDao.getRecent(limit).map(cacheMapper::toDomain)
    }

  override suspend fun saveRecentLocation(location: LocationResult) =
    withContext(Dispatchers.IO) {
      recentLocationDao.insert(cacheMapper.toEntity(location))
    }

  override suspend fun searchLocations(query: String): List<LocationResult> =
    withContext(Dispatchers.IO) {
      geocodingApiService.searchLocations(query)
        .results
        ?.map(dtoMapper::toDomain)
        .orEmpty()
    }
}
