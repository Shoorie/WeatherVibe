package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale
import kotlin.math.roundToInt

@Factory
internal class HomeStateFactory {

  fun create(data: WeatherData): Loaded =
    Loaded(
      currentWeather = createCurrentWeather(data),
      dailyForecast = createDailyForecast(data.dailyForecast),
      header = createHeader(data),
      hourlyForecast = createHourlyForecast(data.hourlyForecast),
      metrics = createMetrics(data),
      sunriseSunset = createSunriseSunset(data.dailyForecast)
    )

  private fun createHeader(data: WeatherData): HeaderUiState =
    HeaderUiState(
      cityName = data.cityName,
      dateLabel = formatDate()
    )

  private fun createCurrentWeather(
    data: WeatherData
  ): CurrentWeatherUiState {
    val today = data.dailyForecast.firstOrNull()
    return CurrentWeatherUiState(
      conditionEmoji = data.condition.emoji,
      conditionLabel = data.condition.label,
      currentTemperature = formatTemperature(data.currentTemperature),
      feelsLikeTemperature = formatTemperature(data.apparentTemperature),
      highTemperature = formatTemperature(today?.maxTemperature ?: data.currentTemperature),
      lowTemperature = formatTemperature(today?.minTemperature ?: data.currentTemperature)
    )
  }

  private fun createHourlyForecast(
    hours: List<HourlyWeather>
  ): List<HourlyForecastUiState> =
    hours.mapIndexed { index, hour ->
      HourlyForecastUiState(
        conditionEmoji = hour.condition.emoji,
        isCurrentHour = index == 0,
        temperature = formatTemperature(hour.temperature),
        timeLabel = formatHourLabel(hour.time)
      )
    }

  private fun createDailyForecast(
    days: List<DailyWeather>
  ): List<DailyForecastUiState> =
    days.map { day ->
      DailyForecastUiState(
        conditionEmoji = day.condition.emoji,
        dayLabel = formatDayLabel(day.date),
        maxTemperature = formatTemperature(day.maxTemperature),
        minTemperature = formatTemperature(day.minTemperature)
      )
    }

  private fun createMetrics(data: WeatherData): MetricsUiState {
    val today = data.dailyForecast.firstOrNull()
    return MetricsUiState(
      cloudCoverValue = formatPercent(data.cloudCover),
      dewPointValue = formatTemperature(data.dewPoint),
      humidityValue = formatPercent(data.humidity),
      precipitationAmountValue = formatMillimeters(today?.precipitationSum ?: 0.0),
      precipitationValue = formatPercent(
        data.hourlyForecast.firstOrNull()?.precipitationProbability ?: 0
      ),
      pressureValue = formatPressure(data.surfacePressure),
      uvIndexValue = formatUvIndex(today?.uvIndexMax ?: 0.0),
      visibilityValue = formatVisibility(data.visibility),
      windDirectionValue = formatWindDirection(data.windDirection),
      windGustsValue = formatWindSpeed(data.windGusts),
      windSpeedMaxValue = formatWindSpeed(today?.windSpeedMax ?: 0.0),
      windSpeedValue = formatWindSpeed(data.windSpeed)
    )
  }

  private fun createSunriseSunset(
    days: List<DailyWeather>
  ): SunriseSunsetUiState {
    val today = days.firstOrNull()
    return SunriseSunsetUiState(
      sunriseTime = formatSunTime(today?.sunrise),
      sunsetTime = formatSunTime(today?.sunset)
    )
  }

  private fun formatTemperature(value: Double): String =
    "${value.roundToInt()}$DEGREE_SYMBOL"

  private fun formatPercent(value: Int): String =
    "$value$PERCENT_SYMBOL"

  private fun formatWindSpeed(value: Double): String =
    "${value.roundToInt()} $WIND_SPEED_UNIT"

  private fun formatPressure(value: Double): String =
    "${value.roundToInt()} $PRESSURE_UNIT"

  private fun formatMillimeters(value: Double): String =
    String.format(Locale.US, MILLIMETERS_FORMAT, value)

  // TODO [azalewski on 21/03/2026]: If an if–else statement doesn’t fit within 100
  //  characters on a single line, use a when instead.
  private fun formatVisibility(meters: Double): String {
    val km = meters / METERS_PER_KM
    return if (km >= VISIBILITY_KM_THRESHOLD) {
      "${km.roundToInt()} $VISIBILITY_UNIT_KM"
    } else {
      "${meters.roundToInt()} $VISIBILITY_UNIT_M"
    }
  }

  private fun formatUvIndex(value: Double): String =
    String.format(Locale.US, UV_INDEX_FORMAT, value)

  private fun formatDate(): String = runCatching {
    LocalDate.now().format(ofPattern(DATE_FORMAT, Locale.ENGLISH))
  }.getOrDefault("")

  private fun formatHourLabel(time: String): String = runCatching {
    LocalDateTime.parse(
      /* text = */ time,
      /* formatter = */ ofPattern(TIME_INPUT_FORMAT)
    ).format(ofPattern(TIME_OUTPUT_FORMAT))
  }.getOrDefault(time)

  private fun formatDayLabel(date: String): String =
    runCatching {
      val parsed = LocalDate.parse(date)
      if (parsed == LocalDate.now()) TODAY_LABEL
      else parsed.format(ofPattern(DAY_FORMAT, Locale.ENGLISH))
    }.getOrDefault(date)

  private fun formatWindDirection(degrees: Double): String {
    val index = ((degrees / DIRECTION_STEP) + DIRECTION_OFFSET)
      .toInt() % WIND_DIRECTIONS.size
    return WIND_DIRECTIONS[index]
  }

  private fun formatSunTime(isoTime: String?): String {
    if (isoTime.isNullOrEmpty()) return ""
    return runCatching {
      LocalDateTime.parse(
        /* text = */ isoTime,
        /* formatter = */ ofPattern(TIME_INPUT_FORMAT)
      ).format(ofPattern(TIME_OUTPUT_FORMAT))
    }.getOrDefault(isoTime)
  }

  // TODO [azalewski on 21/03/2026]: Some strings should be moved to resources.
  private companion object {
    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val DEGREE_SYMBOL = "°"
    const val DIRECTION_OFFSET = 0.5
    const val DIRECTION_STEP = 45.0
    const val METERS_PER_KM = 1000.0
    const val MILLIMETERS_FORMAT = "%.1f mm"
    const val PERCENT_SYMBOL = "%"
    const val PRESSURE_UNIT = "hPa"
    const val TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val TIME_OUTPUT_FORMAT = "HH:mm"
    const val TODAY_LABEL = "Today"
    const val UV_INDEX_FORMAT = "%.1f"
    const val VISIBILITY_KM_THRESHOLD = 1.0
    const val VISIBILITY_UNIT_KM = "km"
    const val VISIBILITY_UNIT_M = "m"
    const val WIND_SPEED_UNIT = "km/h"
    val WIND_DIRECTIONS = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
  }
}
