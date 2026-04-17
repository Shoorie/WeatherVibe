package com.weather.vibe.data.airquality.repository

import com.weather.vibe.data.airquality.remote.api.AirQualityApiService
import com.weather.vibe.data.airquality.remote.mapper.AirQualityDtoMapper
import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.repository.AirQualityRepository
import com.weather.vibe.domain.weather.model.Coordinates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [AirQualityRepository::class])
internal class DefaultAirQualityRepository(
  private val apiService: AirQualityApiService,
  private val dtoMapper: AirQualityDtoMapper
) : AirQualityRepository {

  override suspend fun getAirQuality(coordinates: Coordinates): AirQuality =
    withContext(Dispatchers.IO) {
      val response = apiService.getAirQuality(coordinates)
      dtoMapper.toDomain(
        response = response,
        coordinates = coordinates
      )
    }
}
