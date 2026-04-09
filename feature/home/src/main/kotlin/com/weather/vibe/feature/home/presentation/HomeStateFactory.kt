package com.weather.vibe.feature.home.presentation

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.usecase.FindCurrentHourIndex
import com.weather.vibe.domain.weather.usecase.ResolveTodayTemperatureBounds
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Factory
internal class HomeStateFactory(
  private val findCurrentHourIndex: FindCurrentHourIndex,
  private val metricsFactory: MetricsStateFactory,
  private val playlistFactory: PlaylistStateFactory,
  private val resolveTodayTemperatureBounds: ResolveTodayTemperatureBounds,
  private val resources: HomeResources,
  private val sunriseSunsetFactory: SunriseSunsetStateFactory,
  private val temperatureFormatter: TemperatureFormatter,
  private val timeProvider: TimeProvider
) {

  fun applyWeatherSuggestion(
    briefing: BriefingUiState,
    current: HomeUiState,
    playlist: PlaylistUiState
  ): HomeUiState =
    when (current is Loaded) {
      true -> current.copy(briefing = briefing, playlist = playlist)
      false -> current
    }

  fun create(data: WeatherData, temperatureUnit: TemperatureUnit = CELSIUS): Loaded {
    val today = timeProvider.today()
    val currentHourIndex = findCurrentHourIndex(
      hours = data.hourlyForecast.map { it.time }
    )
    return Loaded(
      currentWeather = createCurrentWeather(data, temperatureUnit),
      dailyForecast = createDailyForecast(data.dailyForecast, temperatureUnit, today),
      detailsSections = metricsFactory.create(data, temperatureUnit),
      header = createHeader(data, today),
      hourlyForecast = createHourlyForecast(data.hourlyForecast, temperatureUnit, currentHourIndex),
      sunriseSunset = sunriseSunsetFactory.create(data.dailyForecast)
    )
  }

  fun createPlaylist(suggestion: WeatherSuggestion): PlaylistUiState.Loaded =
    playlistFactory.create(suggestion)

  fun reformatTemperatures(
    current: HomeUiState,
    data: WeatherData,
    temperatureUnit: TemperatureUnit
  ): HomeUiState {
    val loaded = current as? Loaded ?: return current
    return create(data, temperatureUnit).copy(
      briefing = loaded.briefing,
      playlist = loaded.playlist
    )
  }

  private fun createHeader(data: WeatherData, today: LocalDate): HeaderUiState =
    HeaderUiState(
      cityName = data.cityName,
      dateLabel = today.format(DATE_FORMATTER)
    )

  private fun createCurrentWeather(
    data: WeatherData,
    unit: TemperatureUnit
  ): CurrentWeatherUiState {
    val bounds = resolveTodayTemperatureBounds(data)
    return CurrentWeatherUiState(
      conditionEmoji = data.condition.emoji,
      conditionLabel = resources.conditionLabel(data.condition),
      currentTemperature = temperatureFormatter.format(celsius = data.currentTemperature, unit = unit),
      feelsLikeTemperature = temperatureFormatter.format(celsius = data.apparentTemperature, unit = unit),
      highTemperature = temperatureFormatter.format(celsius = bounds.max, unit = unit),
      lowTemperature = temperatureFormatter.format(celsius = bounds.min, unit = unit)
    )
  }

  private fun createHourlyForecast(
    hours: List<HourlyWeather>,
    unit: TemperatureUnit,
    currentHourIndex: Int
  ): List<HourlyForecastUiState> =
    hours.mapIndexed { index, hour ->
      HourlyForecastUiState(
        conditionEmoji = hour.condition.emoji,
        isCurrentHour = index == currentHourIndex,
        temperature = temperatureFormatter.format(celsius = hour.temperature, unit = unit),
        timeLabel = formatHourLabel(hour.time)
      )
    }

  private fun createDailyForecast(
    days: List<DailyWeather>,
    unit: TemperatureUnit,
    today: LocalDate
  ): List<DailyForecastUiState> =
    days.map { day ->
      DailyForecastUiState(
        conditionEmoji = day.condition.emoji,
        dayLabel = formatDayLabel(day.date, today),
        maxTemperature = temperatureFormatter.format(celsius = day.maxTemperature, unit = unit),
        minTemperature = temperatureFormatter.format(celsius = day.minTemperature, unit = unit)
      )
    }

  private fun formatHourLabel(time: LocalDateTime): String =
    time.format(TIME_OUTPUT_FORMATTER)

  private fun formatDayLabel(date: LocalDate, today: LocalDate): String =
    if (date == today) resources.todayLabel()
    else date.format(DAY_FORMATTER)

  private companion object {

    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val TIME_OUTPUT_FORMAT = "HH:mm"

    val DATE_FORMATTER: DateTimeFormatter
      get() = ofPattern(DATE_FORMAT, Locale.getDefault())

    val DAY_FORMATTER: DateTimeFormatter
      get() = ofPattern(DAY_FORMAT, Locale.getDefault())

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
