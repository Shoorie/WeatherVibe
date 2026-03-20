package com.weather.vibe.data.remote.api

import com.weather.vibe.data.remote.dto.GeocodingResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class GeocodingApiService(private val httpClient: HttpClient) {

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
