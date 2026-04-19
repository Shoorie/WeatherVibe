package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.DailyTemperatureRange
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.ForecastSectionUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Factory
internal class ForecastSectionFactory(
  private val resources: HomeResources,
  private val sunriseSunsetFactory: SunriseSunsetStateFactory,
  private val temperature: TemperatureFormatter,
  private val timeProvider: TimeProvider,
  private val useCases: ForecastUseCases
) {

  fun create(data: WeatherData, unit: TemperatureUnit): ForecastSectionUiState {

    val today = timeProvider.today()
    val forecastHours = data.hourlyForecast.map { it.time }
    val currentHourIndex = useCases.findCurrentHourIndex(hours = forecastHours)
    val sunInfo = useCases.resolveTodaySunInfo(data.dailyForecast)

    return ForecastSectionUiState(
      currentWeather = createCurrentWeather(data, unit),
      dailyForecast = createDailyForecast(data, unit, today),
      header = createHeader(data, today),
      hourlyForecast = createHourlyForecast(data.hourlyForecast, unit, currentHourIndex),
      sunriseSunset = sunriseSunsetFactory.create(sunInfo)
    )
  }

  private fun createHeader(data: WeatherData, today: LocalDate): HeaderUiState =
    HeaderUiState(
      cityName = data.coordinates.name,
      dateLabel = today.format(dateFormatter)
    )

  private fun createCurrentWeather(
    data: WeatherData,
    unit: TemperatureUnit
  ): CurrentWeatherUiState {

    val bounds = useCases.resolveTodayTemperatureBounds(data)

    return CurrentWeatherUiState(
      conditionEmoji = data.condition.emoji,
      conditionLabel = resources.conditionLabel(data.condition),
      currentTemperature = data.currentTemperature.formatted(unit),
      feelsLikeTemperature = data.apparentTemperature.formatted(unit),
      highTemperature = bounds.max.formatted(unit),
      lowTemperature = bounds.min.formatted(unit)
    )
  }

  private fun createHourlyForecast(
    hours: List<HourlyWeather>,
    unit: TemperatureUnit,
    currentHourIndex: Int
  ): HourlyForecastsUiState =
    HourlyForecastsUiState(
      items = hours.mapIndexed { index, hour ->
        val isCurrentHour = index == currentHourIndex
        HourlyForecastUiState(
          conditionEmoji = hour.condition.emoji,
          isCurrentHour = isCurrentHour,
          temperature = hour.temperature.formatted(unit),
          timeLabel = if (isCurrentHour) resources.nowLabel() else formatHourLabel(hour.time)
        )
      }.toImmutableList()
    )

  private fun createDailyForecast(
    data: WeatherData,
    unit: TemperatureUnit,
    today: LocalDate
  ): DailyForecastsUiState {

    val ranges = useCases.buildDailyTemperatureRanges(
      days = data.dailyForecast,
      currentTemperatureCelsius = data.currentTemperature,
      unit = unit,
      today = today
    )
    return DailyForecastsUiState(
      items = data.dailyForecast.zip(ranges).map { (day, range) ->
        DailyForecastUiState(
          conditionEmoji = day.condition.emoji,
          conditionLabel = resources.conditionLabel(day.condition),
          dayLabel = formatDayLabel(day.date, today),
          isToday = day.date == today,
          maxTemperature = day.maxTemperature.formatted(unit),
          minTemperature = day.minTemperature.formatted(unit),
          range = toRangeUiState(range)
        )
      }.toImmutableList()
    )
  }

  private fun toRangeUiState(range: DailyTemperatureRange): DailyRangeUiState =
    DailyRangeUiState(
      startFraction = range.startFraction,
      endFraction = range.endFraction,
      currentFraction = range.currentFraction
    )

  private fun formatHourLabel(time: LocalDateTime): String =
    time.format(TIME_OUTPUT_FORMATTER)

  private fun formatDayLabel(date: LocalDate, today: LocalDate): String =
    when (date) {
      today -> resources.todayLabel()
      else -> date.format(dayFormatter)
    }

  private fun Double.formatted(unit: TemperatureUnit): String =
    temperature.format(celsius = this, unit = unit)

  private val dateFormatter: DateTimeFormatter
    get() = ofPattern(DATE_FORMAT, Locale.getDefault())

  private val dayFormatter: DateTimeFormatter
    get() = ofPattern(DAY_FORMAT, Locale.getDefault())

  private companion object {

    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val TIME_OUTPUT_FORMAT = "HH:mm"

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
