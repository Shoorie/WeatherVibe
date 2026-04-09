package com.weather.vibe.data.weather.mapper

import com.weather.vibe.data.weather.remote.dto.DailyDataDto
import com.weather.vibe.data.weather.remote.dto.ForecastResponseDto
import com.weather.vibe.data.weather.remote.dto.HourlyDataDto
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime

@Factory
internal class WeatherDtoMapper {

  fun toDomain(response: ForecastResponseDto, cityName: String): WeatherData {

    val current = requireNotNull(response.currentWeather)
    val hourly = response.hourly ?: return currentOnlyWeather(response, cityName)

    val currentHourIndex = hourly.time
      .indexOfFirst { it.startsWith(current.time.take(HOUR_PREFIX_LENGTH)) }
      .coerceAtLeast(0)

    val hourlyForecasts = buildHourlyForecasts(hourly, currentHourIndex)
    val dailyForecasts = buildDailyForecasts(response.daily)

    return WeatherData(
      apparentTemperature = hourly.apparentTemperature.getOrElse(currentHourIndex) { current.temperature },
      cityName = cityName,
      cloudCover = hourly.cloudcover.getOrElse(currentHourIndex) { 0 },
      condition = WeatherCondition.fromWmoCode(current.weathercode),
      currentTemperature = current.temperature,
      dailyForecast = dailyForecasts,
      dewPoint = hourly.dewpoint2m.getOrElse(currentHourIndex) { 0.0 },
      hourlyForecast = hourlyForecasts,
      humidity = hourly.relativeHumidity2m.getOrElse(currentHourIndex) { 0 },
      isDay = current.isDay == 1,
      latitude = response.latitude,
      longitude = response.longitude,
      precipitation = hourly.precipitation.getOrElse(currentHourIndex) { 0.0 },
      surfacePressure = hourly.surfacePressure.getOrElse(currentHourIndex) { 0.0 },
      visibility = hourly.visibility.getOrElse(currentHourIndex) { 0.0 },
      windDirection = current.winddirection,
      windGusts = hourly.windgusts10m.getOrElse(currentHourIndex) { 0.0 },
      windSpeed = current.windspeed
    )
  }

  private fun currentOnlyWeather(
    response: ForecastResponseDto,
    cityName: String
  ): WeatherData {
    val current = requireNotNull(response.currentWeather)
    return WeatherData(
      apparentTemperature = current.temperature,
      cityName = cityName,
      condition = WeatherCondition.fromWmoCode(current.weathercode),
      currentTemperature = current.temperature,
      dailyForecast = emptyList(),
      hourlyForecast = emptyList(),
      humidity = 0,
      isDay = current.isDay == 1,
      latitude = response.latitude,
      longitude = response.longitude,
      windDirection = current.winddirection,
      windSpeed = current.windspeed
    )
  }

  private fun buildHourlyForecasts(
    hourly: HourlyDataDto,
    startIndex: Int
  ): List<HourlyWeather> {
    val endIndex = minOf(startIndex + HOURLY_FORECAST_WINDOW, hourly.time.size)
    return (startIndex until endIndex).mapNotNull { index ->
      val time = parseLocalDateTime(hourly.time.getOrNull(index)) ?: return@mapNotNull null
      HourlyWeather(
        apparentTemperature = hourly.apparentTemperature.getOrElse(index) { 0.0 },
        cloudCover = hourly.cloudcover.getOrElse(index) { 0 },
        condition = WeatherCondition.fromWmoCode(hourly.weathercode.getOrElse(index) { 0 }),
        dewPoint = hourly.dewpoint2m.getOrElse(index) { 0.0 },
        humidity = hourly.relativeHumidity2m.getOrElse(index) { 0 },
        precipitation = hourly.precipitation.getOrElse(index) { 0.0 },
        precipitationProbability = hourly.precipitationProbability.getOrElse(index) { 0 },
        surfacePressure = hourly.surfacePressure.getOrElse(index) { 0.0 },
        temperature = hourly.temperature2m.getOrElse(index) { 0.0 },
        time = time,
        visibility = hourly.visibility.getOrElse(index) { 0.0 },
        windGusts = hourly.windgusts10m.getOrElse(index) { 0.0 },
        windSpeed = hourly.windspeed10m.getOrElse(index) { 0.0 }
      )
    }
  }

  private fun buildDailyForecasts(daily: DailyDataDto?): List<DailyWeather> =
    daily?.time?.indices?.mapNotNull { index ->
      val date = parseLocalDate(daily.time.getOrNull(index)) ?: return@mapNotNull null
      DailyWeather(
        condition = WeatherCondition.fromWmoCode(daily.weathercode.getOrElse(index) { 0 }),
        date = date,
        maxTemperature = daily.temperature2mMax.getOrElse(index) { 0.0 },
        minTemperature = daily.temperature2mMin.getOrElse(index) { 0.0 },
        precipitationProbability = daily.precipitationProbabilityMax.getOrElse(index) { 0 },
        precipitationSum = daily.precipitationSum.getOrElse(index) { 0.0 },
        sunrise = parseLocalDateTime(daily.sunrise.getOrNull(index)),
        sunset = parseLocalDateTime(daily.sunset.getOrNull(index)),
        uvIndexMax = daily.uvIndexMax.getOrElse(index) { 0.0 },
        windGustsMax = daily.windgusts10mMax.getOrElse(index) { 0.0 },
        windSpeedMax = daily.windspeed10mMax.getOrElse(index) { 0.0 }
      )
    }.orEmpty()

  private fun parseLocalDateTime(value: String?): LocalDateTime? =
    value?.takeIf { it.isNotEmpty() }
      ?.let { runCatching { LocalDateTime.parse(it) }.getOrNull() }

  private fun parseLocalDate(value: String?): LocalDate? =
    value?.takeIf { it.isNotEmpty() }
      ?.let { runCatching { LocalDate.parse(it) }.getOrNull() }

  private companion object {
    const val HOUR_PREFIX_LENGTH = 13
    const val HOURLY_FORECAST_WINDOW = 24
  }
}
