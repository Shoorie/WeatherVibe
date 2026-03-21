package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW
import com.weather.vibe.domain.weather.model.WeatherData

internal class WeatherDataPreviewParameterProvider :
  PreviewParameterProvider<WeatherData> {

  private val warmDay: WeatherData =
    WeatherData(
      cityName = "Zielona Góra",
      condition = PARTLY_CLOUDY,
      currentTemperature = 18.5,
      dailyForecast = listOf(
        DailyWeather("2024-01-15", 22.0, 14.0, PARTLY_CLOUDY, 20)
      ),
      hourlyForecast = emptyList(),
      humidity = 65,
      isDay = true,
      latitude = 51.9354,
      longitude = 15.5064,
      windDirection = 225.0,
      windSpeed = 12.0
    )

  private val coldNight: WeatherData =
    WeatherData(
      cityName = "Toruń",
      condition = SNOW,
      currentTemperature = -5.0,
      dailyForecast = listOf(DailyWeather("2024-01-15", 0.0, -8.0, SNOW, 80)),
      hourlyForecast = emptyList(),
      humidity = 90,
      isDay = false,
      latitude = 53.0138,
      longitude = 18.5984,
      windDirection = 90.0,
      windSpeed = 25.0
    )

  override val values: Sequence<WeatherData> =
    sequenceOf(warmDay, coldNight)
}
