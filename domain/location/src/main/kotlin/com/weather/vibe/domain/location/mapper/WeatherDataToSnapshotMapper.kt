package com.weather.vibe.domain.location.mapper

import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import java.time.Instant

@Factory
class WeatherDataToSnapshotMapper {

  fun toSnapshot(
    locationId: Long,
    data: WeatherData,
    capturedAt: Instant
  ): LocationWeatherSnapshot {
    val today = data.dailyForecast.firstOrNull()
    val upcomingHours = data.hourlyForecast.take(HOURLY_POINTS)
    return LocationWeatherSnapshot(
      condition = SimplifiedCondition.from(condition = data.condition),
      feelsLikeC = data.apparentTemperature,
      highC = today?.maxTemperature ?: data.currentTemperature,
      hourlyTemperaturesC = upcomingHours.map(HourlyWeather::temperature),
      humidityPercent = data.humidity,
      isDay = data.isDay,
      locationId = locationId,
      lowC = today?.minTemperature ?: data.currentTemperature,
      precipitationChancePercent = upcomingHours.maxOfOrNull { it.precipitationProbability } ?: 0,
      temperatureC = data.currentTemperature,
      updatedAt = capturedAt,
      windKph = data.windSpeed
    )
  }

  private companion object {
    const val HOURLY_POINTS = 24
  }
}
