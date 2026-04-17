package com.weather.vibe.data.airquality.remote.api

import com.weather.vibe.data.airquality.remote.dto.AirQualityResponseDto
import com.weather.vibe.data.airquality.remote.dto.PollenResponseDto
import com.weather.vibe.domain.weather.model.Coordinates
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single(binds = [AirQualityApiService::class])
internal class DefaultAirQualityApiService(
  private val httpClient: HttpClient
) : AirQualityApiService {

  override suspend fun getAirQuality(coordinates: Coordinates): AirQualityResponseDto =
    httpClient.get(BASE_URL) {
      parameter("latitude", coordinates.latitude)
      parameter("longitude", coordinates.longitude)
      parameter("current", AIR_QUALITY_VARIABLES)
      parameter("timezone", TIMEZONE_AUTO)
    }.body()

  override suspend fun getPollen(coordinates: Coordinates): PollenResponseDto =
    httpClient.get(BASE_URL) {
      parameter("latitude", coordinates.latitude)
      parameter("longitude", coordinates.longitude)
      parameter("current", POLLEN_VARIABLES)
      parameter("timezone", TIMEZONE_AUTO)
    }.body()

  private companion object {
    const val BASE_URL = "https://air-quality-api.open-meteo.com/v1/air-quality"
    const val AIR_QUALITY_VARIABLES = "european_aqi"
    const val POLLEN_VARIABLES =
      "alder_pollen,birch_pollen,grass_pollen,mugwort_pollen,olive_pollen,ragweed_pollen"
    const val TIMEZONE_AUTO = "auto"
  }
}
