package com.weather.vibe.data.location.remote.api

import com.weather.vibe.data.location.remote.dto.GeocodingResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
internal class GeocodingApiService(private val httpClient: HttpClient) {

  suspend fun searchLocations(query: String): GeocodingResponseDto =
    httpClient.get(BASE_URL) {
      parameter("name", query)
      parameter("count", DEFAULT_COUNT)
      parameter("language", LANGUAGE)
      parameter("format", FORMAT)
    }.body()

  private companion object {
    const val BASE_URL = "https://geocoding-api.open-meteo.com/v1/search"
    const val DEFAULT_COUNT = 10
    const val FORMAT = "json"
    const val LANGUAGE = "pl"
  }
}
