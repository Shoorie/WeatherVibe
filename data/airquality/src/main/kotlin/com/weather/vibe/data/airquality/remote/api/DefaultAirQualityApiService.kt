package com.weather.vibe.data.airquality.remote.api

import com.weather.vibe.data.airquality.remote.dto.AirQualityResponseDto
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
      parameter("current", CURRENT_VARIABLES)
      parameter("timezone", TIMEZONE_AUTO)
    }.body()

  private companion object {
    const val BASE_URL = "https://air-quality-api.open-meteo.com/v1/air-quality"
    const val CURRENT_VARIABLES = "european_aqi"
    const val TIMEZONE_AUTO = "auto"
  }
}
