package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.usecase.ConvertTemperature
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
  private val convertTemperature: ConvertTemperature,
  private val metricsFactory: MetricsStateFactory,
  private val playlistFactory: PlaylistStateFactory,
  private val resources: HomeResources,
  private val sunriseSunsetFactory: SunriseSunsetStateFactory
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

  fun create(data: WeatherData, temperatureUnit: TemperatureUnit = CELSIUS): Loaded =
    Loaded(
      currentWeather = createCurrentWeather(data, temperatureUnit),
      dailyForecast = createDailyForecast(data.dailyForecast, temperatureUnit),
      detailsSections = metricsFactory.create(data, temperatureUnit),
      header = createHeader(data),
      hourlyForecast = createHourlyForecast(data.hourlyForecast, temperatureUnit),
      sunriseSunset = sunriseSunsetFactory.create(data.dailyForecast)
    )

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

  private fun createHeader(data: WeatherData): HeaderUiState =
    HeaderUiState(
      cityName = data.cityName,
      dateLabel = formatDate()
    )

  private fun createCurrentWeather(
    data: WeatherData,
    unit: TemperatureUnit
  ): CurrentWeatherUiState {
    val today = data.dailyForecast.firstOrNull()
    return CurrentWeatherUiState(
      conditionEmoji = data.condition.emoji,
      conditionLabel = resources.conditionLabel(data.condition),
      currentTemperature = convertTemperature(celsius = data.currentTemperature, unit = unit),
      feelsLikeTemperature = convertTemperature(celsius = data.apparentTemperature, unit = unit),
      highTemperature = convertTemperature(
        celsius = today?.maxTemperature ?: data.currentTemperature,
        unit = unit
      ),
      lowTemperature = convertTemperature(
        celsius = today?.minTemperature ?: data.currentTemperature,
        unit = unit
      )
    )
  }

  private fun createHourlyForecast(
    hours: List<HourlyWeather>,
    unit: TemperatureUnit
  ): List<HourlyForecastUiState> =
    hours.mapIndexed { index, hour ->
      HourlyForecastUiState(
        conditionEmoji = hour.condition.emoji,
        isCurrentHour = index == CURRENT_HOUR_INDEX,
        temperature = convertTemperature(celsius = hour.temperature, unit = unit),
        timeLabel = formatHourLabel(hour.time)
      )
    }

  private fun createDailyForecast(
    days: List<DailyWeather>,
    unit: TemperatureUnit
  ): List<DailyForecastUiState> =
    days.map { day ->
      DailyForecastUiState(
        conditionEmoji = day.condition.emoji,
        dayLabel = formatDayLabel(day.date),
        maxTemperature = convertTemperature(celsius = day.maxTemperature, unit = unit),
        minTemperature = convertTemperature(celsius = day.minTemperature, unit = unit)
      )
    }

  private fun formatDate(): String =
    LocalDate.now().format(DATE_FORMATTER)

  private fun formatHourLabel(time: String): String =
    runCatching {
      LocalDateTime
        .parse(time, TIME_INPUT_FORMATTER)
        .format(TIME_OUTPUT_FORMATTER)
    }.getOrDefault(time)

  private fun formatDayLabel(date: String): String =
    runCatching {
      val parsed = LocalDate.parse(date)
      if (parsed == LocalDate.now()) resources.todayLabel()
      else parsed.format(DAY_FORMATTER)
    }.getOrDefault(date)

  private companion object {

    const val CURRENT_HOUR_INDEX = 0
    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val TIME_OUTPUT_FORMAT = "HH:mm"

    val DATE_FORMATTER: DateTimeFormatter?
      get() = ofPattern(DATE_FORMAT, Locale.getDefault())

    val DAY_FORMATTER: DateTimeFormatter?
      get() = ofPattern(DAY_FORMAT, Locale.getDefault())

    val TIME_INPUT_FORMATTER: DateTimeFormatter? =
      ofPattern(TIME_INPUT_FORMAT)

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter? =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
