package com.weather.vibe.data.weather.mapper

import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity
import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto
import com.weather.vibe.data.weather.remote.dto.LocationResultDto
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.LocationResult
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherData
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun ForecastResponseDto.toWeatherData(cityName: String): WeatherData {
  val current = requireNotNull(currentWeather)
  val hourly = hourly ?: return WeatherData(
    cityName = cityName,
    latitude = latitude,
    longitude = longitude,
    currentTemperature = current.temperature,
    condition = WeatherCondition.fromWmoCode(current.weathercode),
    windSpeed = current.windspeed,
    windDirection = current.winddirection,
    humidity = 0,
    isDay = current.isDay == 1,
    hourlyForecast = emptyList(),
    dailyForecast = emptyList()
  )
  val daily = daily

  val currentHourIndex = hourly.time
    .indexOfFirst { it.startsWith(current.time.take(13)) }
    .coerceAtLeast(0)

  val hourlyForecasts = (currentHourIndex until minOf(currentHourIndex + 24, hourly.time.size))
    .map { i ->
      HourlyWeather(
        time = hourly.time[i],
        temperature = hourly.temperature2m.getOrElse(i) { 0.0 },
        condition = WeatherCondition.fromWmoCode(hourly.weathercode.getOrElse(i) { 0 }),
        humidity = hourly.relativeHumidity2m.getOrElse(i) { 0 },
        windSpeed = hourly.windspeed10m.getOrElse(i) { 0.0 },
        precipitationProbability = hourly.precipitationProbability.getOrElse(i) { 0 }
      )
    }

  val dailyForecasts = daily?.time?.indices?.map { i ->
    DailyWeather(
      date = daily.time[i],
      maxTemperature = daily.temperature2mMax.getOrElse(i) { 0.0 },
      minTemperature = daily.temperature2mMin.getOrElse(i) { 0.0 },
      condition = WeatherCondition.fromWmoCode(daily.weathercode.getOrElse(i) { 0 }),
      precipitationProbability = daily.precipitationProbabilityMax.getOrElse(i) { 0 }
    )
  } ?: emptyList()

  val currentHumidity = hourly.relativeHumidity2m.getOrElse(currentHourIndex) { 0 }

  return WeatherData(
    cityName = cityName,
    latitude = latitude,
    longitude = longitude,
    currentTemperature = current.temperature,
    condition = WeatherCondition.fromWmoCode(current.weathercode),
    windSpeed = current.windspeed,
    windDirection = current.winddirection,
    humidity = currentHumidity,
    isDay = current.isDay == 1,
    hourlyForecast = hourlyForecasts,
    dailyForecast = dailyForecasts
  )
}

fun WeatherData.toCacheEntity(): WeatherCacheEntity =
  WeatherCacheEntity(
    locationId = "${latitude},${longitude}",
    cityName = cityName,
    currentTemperature = currentTemperature,
    currentConditionName = condition.name,
    windSpeed = windSpeed,
    windDirection = windDirection,
    humidity = humidity,
    isDay = isDay,
    hourlyForecastJson = json.encodeToString(hourlyForecast),
    dailyForecastJson = json.encodeToString(dailyForecast),
    lastUpdated = System.currentTimeMillis()
  )

fun WeatherCacheEntity.toWeatherData(): WeatherData =
  WeatherData(
    cityName = cityName,
    latitude = locationId.split(",")[0].toDoubleOrNull() ?: 0.0,
    longitude = locationId.split(",")[1].toDoubleOrNull() ?: 0.0,
    currentTemperature = currentTemperature,
    condition = runCatching { WeatherCondition.valueOf(currentConditionName) }
      .getOrDefault(WeatherCondition.UNKNOWN),
    windSpeed = windSpeed,
    windDirection = windDirection,
    humidity = humidity,
    isDay = isDay,
    hourlyForecast = runCatching {
      json.decodeFromString<List<HourlyWeather>>(hourlyForecastJson)
    }.getOrDefault(emptyList()),
    dailyForecast = runCatching {
      json.decodeFromString<List<DailyWeather>>(dailyForecastJson)
    }.getOrDefault(emptyList())
  )

fun LocationResultDto.toLocationResult() =
  LocationResult(
    id = id,
    name = name,
    latitude = latitude,
    longitude = longitude,
    country = country ?: "",
    admin1 = admin1
  )
