package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale
import kotlin.math.roundToInt

@Factory
internal class HomeStateFactory(
  private val metricsFactory: MetricsStateFactory,
  private val resources: HomeResources
) {

  fun create(data: WeatherData): Loaded =
    Loaded(
      currentWeather = createCurrentWeather(data),
      dailyForecast = createDailyForecast(data.dailyForecast),
      header = createHeader(data),
      hourlyForecast = createHourlyForecast(data.hourlyForecast),
      metrics = metricsFactory.create(data),
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
      if (parsed == LocalDate.now()) resources.todayLabel()
      else parsed.format(ofPattern(DAY_FORMAT, Locale.ENGLISH))
    }.getOrDefault(date)

  private fun formatSunTime(isoTime: String?): String {
    if (isoTime.isNullOrEmpty()) return ""
    return runCatching {
      LocalDateTime.parse(
        /* text = */ isoTime,
        /* formatter = */ ofPattern(TIME_INPUT_FORMAT)
      ).format(ofPattern(TIME_OUTPUT_FORMAT))
    }.getOrDefault(isoTime)
  }

  private companion object {
    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val DEGREE_SYMBOL = "°"
    const val TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val TIME_OUTPUT_FORMAT = "HH:mm"
  }
}
