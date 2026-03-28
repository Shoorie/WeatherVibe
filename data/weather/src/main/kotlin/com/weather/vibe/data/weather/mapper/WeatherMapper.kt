package com.weather.vibe.data.weather.mapper

import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity
import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherData
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun ForecastResponseDto.toWeatherData(cityName: String): WeatherData {
  val current = requireNotNull(currentWeather)
  val hourly = hourly ?: return WeatherData(
    apparentTemperature = current.temperature,
    cityName = cityName,
    condition = WeatherCondition.fromWmoCode(current.weathercode),
    currentTemperature = current.temperature,
    dailyForecast = emptyList(),
    hourlyForecast = emptyList(),
    humidity = 0,
    isDay = current.isDay == 1,
    latitude = latitude,
    longitude = longitude,
    windDirection = current.winddirection,
    windSpeed = current.windspeed
  )

  val currentHourIndex = hourly.time
    .indexOfFirst { it.startsWith(current.time.take(13)) }
    .coerceAtLeast(0)

  val hourlyForecasts = (currentHourIndex until minOf(
    currentHourIndex + 24,
    hourly.time.size
  )).map { i ->
    HourlyWeather(
      apparentTemperature = hourly.apparentTemperature.getOrElse(i) { 0.0 },
      cloudCover = hourly.cloudcover.getOrElse(i) { 0 },
      condition = WeatherCondition.fromWmoCode(hourly.weathercode.getOrElse(i) { 0 }),
      dewPoint = hourly.dewpoint2m.getOrElse(i) { 0.0 },
      humidity = hourly.relativeHumidity2m.getOrElse(i) { 0 },
      precipitation = hourly.precipitation.getOrElse(i) { 0.0 },
      precipitationProbability = hourly.precipitationProbability.getOrElse(i) { 0 },
      surfacePressure = hourly.surfacePressure.getOrElse(i) { 0.0 },
      temperature = hourly.temperature2m.getOrElse(i) { 0.0 },
      time = hourly.time[i],
      visibility = hourly.visibility.getOrElse(i) { 0.0 },
      windGusts = hourly.windgusts10m.getOrElse(i) { 0.0 },
      windSpeed = hourly.windspeed10m.getOrElse(i) { 0.0 }
    )
  }

  val dailyForecasts = daily?.time?.indices?.map { i ->
    DailyWeather(
      condition = WeatherCondition.fromWmoCode(
        daily.weathercode.getOrElse(i) { 0 }
      ),
      date = daily.time[i],
      maxTemperature = daily.temperature2mMax.getOrElse(i) { 0.0 },
      minTemperature = daily.temperature2mMin.getOrElse(i) { 0.0 },
      precipitationProbability = daily.precipitationProbabilityMax
        .getOrElse(i) { 0 },
      precipitationSum = daily.precipitationSum.getOrElse(i) { 0.0 },
      sunrise = daily.sunrise.getOrElse(i) { "" },
      sunset = daily.sunset.getOrElse(i) { "" },
      uvIndexMax = daily.uvIndexMax.getOrElse(i) { 0.0 },
      windGustsMax = daily.windgusts10mMax.getOrElse(i) { 0.0 },
      windSpeedMax = daily.windspeed10mMax.getOrElse(i) { 0.0 }
    )
  } ?: emptyList()

  val currentHumidity = hourly.relativeHumidity2m
    .getOrElse(currentHourIndex) { 0 }
  val currentApparentTemp = hourly.apparentTemperature
    .getOrElse(currentHourIndex) { current.temperature }

  return WeatherData(
    apparentTemperature = currentApparentTemp,
    cityName = cityName,
    cloudCover = hourly.cloudcover.getOrElse(currentHourIndex) { 0 },
    condition = WeatherCondition.fromWmoCode(current.weathercode),
    currentTemperature = current.temperature,
    dailyForecast = dailyForecasts,
    dewPoint = hourly.dewpoint2m.getOrElse(currentHourIndex) { 0.0 },
    hourlyForecast = hourlyForecasts,
    humidity = currentHumidity,
    isDay = current.isDay == 1,
    latitude = latitude,
    longitude = longitude,
    precipitation = hourly.precipitation.getOrElse(currentHourIndex) { 0.0 },
    surfacePressure = hourly.surfacePressure.getOrElse(currentHourIndex) { 0.0 },
    visibility = hourly.visibility.getOrElse(currentHourIndex) { 0.0 },
    windDirection = current.winddirection,
    windGusts = hourly.windgusts10m.getOrElse(currentHourIndex) { 0.0 },
    windSpeed = current.windspeed
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

fun WeatherCacheEntity.toWeatherData(): WeatherData {

  val hourlyList = runCatching {
    json.decodeFromString<List<HourlyWeather>>(hourlyForecastJson)
  }.getOrDefault(emptyList())

  val dailyList = runCatching {
    json.decodeFromString<List<DailyWeather>>(dailyForecastJson)
  }.getOrDefault(emptyList())

  val firstHourly = hourlyList.firstOrNull()

  return WeatherData(
    apparentTemperature = firstHourly?.apparentTemperature
      ?: currentTemperature,
    cityName = cityName,
    cloudCover = firstHourly?.cloudCover ?: 0,
    condition = runCatching { WeatherCondition.valueOf(currentConditionName) }
      .getOrDefault(WeatherCondition.UNKNOWN),
    currentTemperature = currentTemperature,
    dailyForecast = dailyList,
    dewPoint = firstHourly?.dewPoint ?: 0.0,
    hourlyForecast = hourlyList,
    humidity = humidity,
    isDay = isDay,
    latitude = locationId.split(",")[0].toDoubleOrNull() ?: 0.0,
    longitude = locationId.split(",")[1].toDoubleOrNull() ?: 0.0,
    precipitation = firstHourly?.precipitation ?: 0.0,
    surfacePressure = firstHourly?.surfacePressure ?: 0.0,
    visibility = firstHourly?.visibility ?: 0.0,
    windDirection = windDirection,
    windGusts = firstHourly?.windGusts ?: 0.0,
    windSpeed = windSpeed
  )
}
