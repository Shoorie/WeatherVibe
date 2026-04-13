package com.weather.vibe.feature.home.presentation

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.usecase.FindCurrentHourIndex
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherMetrics
import com.weather.vibe.domain.weather.usecase.ResolveTodaySunInfo
import com.weather.vibe.domain.weather.usecase.ResolveTodayTemperatureBounds
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
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
  private val factories: HomeFactories,
  private val findCurrentHourIndex: FindCurrentHourIndex,
  private val getCurrentWeatherMetrics: GetCurrentWeatherMetrics,
  private val resolveTodaySunInfo: ResolveTodaySunInfo,
  private val resolveTodayTemperatureBounds: ResolveTodayTemperatureBounds,
  private val resources: HomeResources,
  private val temperature: TemperatureFormatter,
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

  fun applyPlaylist(current: HomeUiState, playlist: PlaylistUiState): HomeUiState =
    when (current is Loaded) {
      true -> current.copy(playlist = playlist)
      false -> current
    }

  fun markGenreAsRejecting(current: HomeUiState, genre: String): HomeUiState {

    if (current !is Loaded) return current
    val loadedPlaylist = current.playlist as? PlaylistUiState.Loaded ?: return current

    return current.copy(
      playlist = loadedPlaylist.copy(
        genres = loadedPlaylist.genres.map {
          if (it.name == genre) it.copy(isRejecting = true) else it
        }
      )
    )
  }

  fun areAllGenresRejected(current: HomeUiState): Boolean {
    val loaded = current as? Loaded ?: return false
    val playlist = loaded.playlist as? PlaylistUiState.Loaded ?: return false
    return playlist.genres.all { it.isRejecting }
  }

  fun isPlaylistLoaded(current: HomeUiState): Boolean =
    (current as? Loaded)?.playlist is PlaylistUiState.Loaded

  fun create(data: WeatherData, unit: TemperatureUnit = CELSIUS): Loaded {

    val today = timeProvider.today()
    val currentHourIndex = findCurrentHourIndex(hours = data.hourlyForecast.map { it.time })
    val currentMetrics = getCurrentWeatherMetrics(data)
    val sunInfo = resolveTodaySunInfo(data.dailyForecast)

    return Loaded(
      currentWeather = createCurrentWeather(data, unit),
      dailyForecast = createDailyForecast(data.dailyForecast, data.currentTemperature, unit, today),
      detailsSections = factories.metrics.create(currentMetrics, unit),
      header = createHeader(data, today),
      hourlyForecast = createHourlyForecast(data.hourlyForecast, unit, currentHourIndex),
      sunriseSunset = factories.sunriseSunset.create(sunInfo)
    )
  }

  fun createPlaylist(suggestion: WeatherSuggestion): PlaylistUiState.Loaded =
    factories.playlist.create(suggestion)

  fun reformatTemperatures(
    current: HomeUiState,
    data: WeatherData,
    unit: TemperatureUnit
  ): HomeUiState {
    val loaded = current as? Loaded ?: return current
    return create(data, unit).copy(
      briefing = loaded.briefing,
      playlist = loaded.playlist
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

    val bounds = resolveTodayTemperatureBounds(data)

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
        HourlyForecastUiState(
          conditionEmoji = hour.condition.emoji,
          isCurrentHour = index == currentHourIndex,
          temperature = hour.temperature.formatted(unit),
          timeLabel = formatHourLabel(hour.time)
        )
      }
    )

  private fun createDailyForecast(
    days: List<DailyWeather>,
    currentTemperature: Double,
    unit: TemperatureUnit,
    today: LocalDate
  ): DailyForecastsUiState {

    val rounded = days.map { day ->
      DailyRangeBounds(
        day = day,
        min = temperature.roundedValue(celsius = day.minTemperature, unit = unit),
        max = temperature.roundedValue(celsius = day.maxTemperature, unit = unit)
      )
    }
    val weekMin = rounded.minOfOrNull { it.min } ?: 0
    val weekMax = rounded.maxOfOrNull { it.max } ?: 0
    val totalRange = (weekMax - weekMin).coerceAtLeast(MIN_RANGE)
    val currentRounded = temperature.roundedValue(celsius = currentTemperature, unit = unit)

    return DailyForecastsUiState(
      items = rounded.map { entry ->
        DailyForecastUiState(
          conditionEmoji = entry.day.condition.emoji,
          conditionLabel = resources.conditionLabel(entry.day.condition),
          dayLabel = formatDayLabel(entry.day.date, today),
          isToday = entry.day.date == today,
          maxTemperature = entry.day.maxTemperature.formatted(unit),
          minTemperature = entry.day.minTemperature.formatted(unit),
          range = buildRange(entry, currentRounded, weekMin, totalRange, today)
        )
      }
    )
  }

  private fun buildRange(
    entry: DailyRangeBounds,
    currentTemperature: Int,
    weekMin: Int,
    totalRange: Int,
    today: LocalDate
  ): DailyRangeUiState {
    val rangeFloat = totalRange.toFloat()
    val start = ((entry.min - weekMin) / rangeFloat).coerceIn(0f, 1f)
    val end = ((entry.max - weekMin) / rangeFloat).coerceIn(0f, 1f)
    val current = currentTemperature
      .takeIf { entry.day.date == today }
      ?.let { ((it - weekMin) / rangeFloat).coerceIn(start, end) }
    return DailyRangeUiState(startFraction = start, endFraction = end, currentFraction = current)
  }

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
    const val MIN_RANGE = 1

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
