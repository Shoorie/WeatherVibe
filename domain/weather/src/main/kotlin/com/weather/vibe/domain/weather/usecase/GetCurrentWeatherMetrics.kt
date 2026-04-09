package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherMetrics
import org.koin.core.annotation.Factory

@Factory
class GetCurrentWeatherMetrics(
  private val computeWindDirection: ComputeWindDirection,
  private val findCurrentHourIndex: FindCurrentHourIndex
) {

  operator fun invoke(data: WeatherData): WeatherMetrics {

    val today = data.dailyForecast.firstOrNull()
    val currentHour = currentHour(data.hourlyForecast)

    return WeatherMetrics(
      cloudCover = data.cloudCover,
      dewPoint = data.dewPoint,
      humidity = data.humidity,
      precipitationProbability = currentHour?.precipitationProbability ?: DEFAULT_PRECIPITATION,
      precipitationSum = today?.precipitationSum ?: DEFAULT_PRECIPITATION_SUM,
      surfacePressure = data.surfacePressure,
      uvIndexMax = today?.uvIndexMax ?: DEFAULT_UV_INDEX,
      visibility = data.visibility,
      windDirection = computeWindDirection(degrees = data.windDirection),
      windGusts = data.windGusts,
      windSpeed = data.windSpeed,
      windSpeedMax = today?.windSpeedMax ?: DEFAULT_WIND_SPEED
    )
  }

  private fun currentHour(hours: List<HourlyWeather>): HourlyWeather? {
    val index = findCurrentHourIndex(hours = hours.map { it.time })
    return hours.getOrNull(index)
  }

  private companion object {
    const val DEFAULT_PRECIPITATION = 0
    const val DEFAULT_PRECIPITATION_SUM = 0.0
    const val DEFAULT_UV_INDEX = 0.0
    const val DEFAULT_WIND_SPEED = 0.0
  }
}
