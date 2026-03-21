package com.weather.vibe.data.weather.repository

import com.weather.vibe.data.weather.mapper.toLocationResult
import com.weather.vibe.data.weather.remote.api.GeocodingApiService
import com.weather.vibe.domain.weather.model.LocationResult
import com.weather.vibe.domain.weather.repository.GeocodingRepository
import org.koin.core.annotation.Single

@Single(binds = [GeocodingRepository::class])
internal class DefaultGeocodingRepository(
  private val apiService: GeocodingApiService
) : GeocodingRepository {

  override suspend fun searchLocations(query: String): List<LocationResult> {
    val response = apiService.searchLocations(query)
    return response.results
      ?.map { it.toLocationResult() }
      .orEmpty()
  }
}
