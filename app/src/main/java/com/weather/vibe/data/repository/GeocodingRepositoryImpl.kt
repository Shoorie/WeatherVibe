package com.weather.vibe.data.repository

import com.weather.vibe.data.remote.api.GeocodingApiService
import com.weather.vibe.data.remote.dto.LocationResultDto
import com.weather.vibe.domain.model.LocationResult
import com.weather.vibe.domain.repository.GeocodingRepository

class GeocodingRepositoryImpl(
    private val apiService: GeocodingApiService
) : GeocodingRepository {

    override suspend fun searchLocations(query: String): Result<List<LocationResult>> =
        runCatching {
            val response = apiService.searchLocations(query)
            response.results?.map { it.toLocationResult() } ?: emptyList()
        }
}

private fun LocationResultDto.toLocationResult() = LocationResult(
    id = id,
    name = name,
    latitude = latitude,
    longitude = longitude,
    country = country ?: "",
    admin1 = admin1
)
