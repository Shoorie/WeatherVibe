package com.weather.vibe.data.airquality.repository

import com.weather.vibe.data.airquality.remote.api.AirQualityApiService
import com.weather.vibe.data.airquality.remote.mapper.AirQualityDtoMapper
import com.weather.vibe.data.airquality.remote.mapper.PollenDtoMapper
import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.model.Pollen
import com.weather.vibe.domain.airquality.repository.AirQualityRepository
import com.weather.vibe.domain.weather.model.Coordinates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [AirQualityRepository::class])
internal class DefaultAirQualityRepository(
  private val apiService: AirQualityApiService,
  private val airQualityDtoMapper: AirQualityDtoMapper,
  private val pollenDtoMapper: PollenDtoMapper
) : AirQualityRepository {

  override suspend fun getAirQuality(coordinates: Coordinates): AirQuality =
    withContext(Dispatchers.IO) {
      val response = apiService.getAirQuality(coordinates)
      airQualityDtoMapper.toDomain(
        response = response,
        coordinates = coordinates
      )
    }

  override suspend fun getPollen(coordinates: Coordinates): Pollen =
    withContext(Dispatchers.IO) {
      val response = apiService.getPollen(coordinates)
      pollenDtoMapper.toDomain(
        response = response,
        coordinates = coordinates
      )
    }
}
