package com.weather.vibe.data.weather.remote.api

import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
internal class WeatherApiService(private val httpClient: HttpClient) {

  suspend fun getForecast(latitude: Double, longitude: Double): ForecastResponseDto =
    httpClient.get(BASE_URL) {
      parameter("latitude", latitude)
      parameter("longitude", longitude)
      parameter("current_weather", true)
      parameter("hourly", HOURLY_VARIABLES)
      parameter("daily", DAILY_VARIABLES)
      parameter("timezone", "auto")
      parameter("forecast_days", 7)
    }.body()

  private companion object {
    const val BASE_URL = "https://api.open-meteo.com/v1/forecast"
    const val HOURLY_VARIABLES =
      "temperature_2m,relative_humidity_2m,weathercode,windspeed_10m,precipitation_probability"
    const val DAILY_VARIABLES =
      "weathercode,temperature_2m_max,temperature_2m_min,precipitation_probability_max"
  }
}
