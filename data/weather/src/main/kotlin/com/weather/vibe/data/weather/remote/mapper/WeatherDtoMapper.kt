package com.weather.vibe.data.weather.remote.mapper

import com.weather.vibe.data.weather.remote.dto.DailyDataDto
import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto
import com.weather.vibe.data.weather.remote.dto.HourlyDataDto
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime

@Factory
internal class WeatherDtoMapper {

  fun toDomain(response: ForecastResponseDto, coordinates: Coordinates): WeatherData {

    val current = requireNotNull(response.currentWeather)
    val hourly = response.hourly ?: return currentOnlySnapshot(response, coordinates)

    val currentIndex = findCurrentHourIndex(hourly.time, current.time)
    val snapshot = snapshotAtCurrentHour(hourly, currentIndex, current.temperature)

    return WeatherData(
      apparentTemperature = snapshot.apparentTemperature,
      cloudCover = snapshot.cloudCover,
      condition = WeatherCondition.fromWmoCode(current.weathercode),
      coordinates = coordinates,
      currentTemperature = current.temperature,
      dailyForecast = buildDailyForecasts(response.daily),
      dewPoint = snapshot.dewPoint,
      hourlyForecast = buildHourlyForecasts(hourly, currentIndex),
      humidity = snapshot.humidity,
      isDay = current.isDay == 1,
      precipitation = snapshot.precipitation,
      surfacePressure = snapshot.surfacePressure,
      visibility = snapshot.visibility,
      windDirection = current.winddirection,
      windGusts = snapshot.windGusts,
      windSpeed = current.windspeed
    )
  }

  private fun currentOnlySnapshot(
    response: ForecastResponseDto,
    coordinates: Coordinates
  ): WeatherData {
    val current = requireNotNull(response.currentWeather)
    return WeatherData(
      apparentTemperature = current.temperature,
      condition = WeatherCondition.fromWmoCode(current.weathercode),
      coordinates = coordinates,
      currentTemperature = current.temperature,
      dailyForecast = emptyList(),
      hourlyForecast = emptyList(),
      humidity = 0,
      isDay = current.isDay == 1,
      windDirection = current.winddirection,
      windSpeed = current.windspeed
    )
  }

  private fun findCurrentHourIndex(hourlyTimes: List<String>, currentTime: String): Int =
    hourlyTimes
      .indexOfFirst { it.startsWith(currentTime.take(HOUR_PREFIX_LENGTH)) }
      .coerceAtLeast(0)

  private fun snapshotAtCurrentHour(
    hourly: HourlyDataDto,
    index: Int,
    fallbackTemperature: Double
  ): CurrentHourSnapshot =
    CurrentHourSnapshot(
      apparentTemperature = hourly.apparentTemperature.getOrElse(index) { fallbackTemperature },
      cloudCover = hourly.cloudcover.atOrZero(index),
      dewPoint = hourly.dewpoint2m.atOrZero(index),
      humidity = hourly.relativeHumidity2m.atOrZero(index),
      precipitation = hourly.precipitation.atOrZero(index),
      surfacePressure = hourly.surfacePressure.atOrZero(index),
      visibility = hourly.visibility.atOrZero(index),
      windGusts = hourly.windgusts10m.atOrZero(index)
    )

  private fun buildHourlyForecasts(
    hourly: HourlyDataDto,
    startIndex: Int
  ): List<HourlyWeather> {
    val endIndex = minOf(startIndex + HOURLY_FORECAST_WINDOW, hourly.time.size)
    return (startIndex until endIndex).mapNotNull { index ->
      val time = parseLocalDateTime(hourly.time.getOrNull(index)) ?: return@mapNotNull null
      HourlyWeather(
        apparentTemperature = hourly.apparentTemperature.atOrZero(index),
        cloudCover = hourly.cloudcover.atOrZero(index),
        condition = WeatherCondition.fromWmoCode(hourly.weathercode.atOrZero(index)),
        dewPoint = hourly.dewpoint2m.atOrZero(index),
        humidity = hourly.relativeHumidity2m.atOrZero(index),
        precipitation = hourly.precipitation.atOrZero(index),
        precipitationProbability = hourly.precipitationProbability.atOrZero(index),
        surfacePressure = hourly.surfacePressure.atOrZero(index),
        temperature = hourly.temperature2m.atOrZero(index),
        time = time,
        visibility = hourly.visibility.atOrZero(index),
        windGusts = hourly.windgusts10m.atOrZero(index),
        windSpeed = hourly.windspeed10m.atOrZero(index)
      )
    }
  }

  private fun buildDailyForecasts(daily: DailyDataDto?): List<DailyWeather> =
    daily?.time?.indices?.mapNotNull { index ->
      val date = parseLocalDate(daily.time.getOrNull(index)) ?: return@mapNotNull null
      DailyWeather(
        condition = WeatherCondition.fromWmoCode(daily.weathercode.atOrZero(index)),
        date = date,
        maxTemperature = daily.temperature2mMax.atOrZero(index),
        minTemperature = daily.temperature2mMin.atOrZero(index),
        precipitationProbability = daily.precipitationProbabilityMax.atOrZero(index),
        precipitationSum = daily.precipitationSum.atOrZero(index),
        sunrise = parseLocalDateTime(daily.sunrise.getOrNull(index)),
        sunset = parseLocalDateTime(daily.sunset.getOrNull(index)),
        uvIndexMax = daily.uvIndexMax.atOrZero(index),
        windGustsMax = daily.windgusts10mMax.atOrZero(index),
        windSpeedMax = daily.windspeed10mMax.atOrZero(index)
      )
    }.orEmpty()

  private fun parseLocalDateTime(value: String?): LocalDateTime? =
    value?.takeIf { it.isNotEmpty() }
      ?.let { runCatching { LocalDateTime.parse(it) }.getOrNull() }

  private fun parseLocalDate(value: String?): LocalDate? =
    value?.takeIf { it.isNotEmpty() }
      ?.let { runCatching { LocalDate.parse(it) }.getOrNull() }

  @JvmName("doubleAtOrZero")
  private fun List<Double>.atOrZero(index: Int): Double =
    getOrElse(index) { 0.0 }

  @JvmName("intAtOrZero")
  private fun List<Int>.atOrZero(index: Int): Int =
    getOrElse(index) { 0 }

  private data class CurrentHourSnapshot(
    val apparentTemperature: Double,
    val cloudCover: Int,
    val dewPoint: Double,
    val humidity: Int,
    val precipitation: Double,
    val surfacePressure: Double,
    val visibility: Double,
    val windGusts: Double
  )

  private companion object {
    const val HOUR_PREFIX_LENGTH = 13
    const val HOURLY_FORECAST_WINDOW = 24
  }
}
