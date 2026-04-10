package com.weather.vibe.data.location.repository

import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.local.mapper.LocationCacheMapper
import com.weather.vibe.data.location.remote.api.GeocodingApiService
import com.weather.vibe.data.location.remote.mapper.LocationDtoMapper
import com.weather.vibe.domain.location.model.Location
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

  override suspend fun getRecentLocations(limit: Int): List<Location> =
    withContext(Dispatchers.IO) {
      recentLocationDao.getRecent(limit).map(cacheMapper::toDomain)
    }

  override suspend fun saveRecentLocation(location: Location) =
    withContext(Dispatchers.IO) {
      recentLocationDao.insert(cacheMapper.toEntity(location))
    }

  override suspend fun searchLocations(query: String): List<Location> =
    withContext(Dispatchers.IO) {
      geocodingApiService.searchLocations(query)
        .results
        ?.map(dtoMapper::toDomain)
        .orEmpty()
    }
}
