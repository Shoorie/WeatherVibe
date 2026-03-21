package com.weather.vibe.data.weather.remote.api

import com.weather.vibe.data.weather.remote.dto.GeocodingResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
internal class GeocodingApiService(private val httpClient: HttpClient) {

  suspend fun searchLocations(query: String, count: Int = 10): GeocodingResponseDto =
    httpClient.get(BASE_URL) {
      parameter("name", query)
      parameter("count", count)
      parameter("language", "pl")
      parameter("format", "json")
    }.body()

  private companion object {
    const val BASE_URL = "https://geocoding-api.open-meteo.com/v1/search"
  }
}
