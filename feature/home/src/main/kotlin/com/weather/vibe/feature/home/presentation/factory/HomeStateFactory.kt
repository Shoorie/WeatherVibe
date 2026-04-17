package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.DailyTemperatureRange
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.usecase.BuildDailyTemperatureRanges
import com.weather.vibe.domain.weather.usecase.FindCurrentHourIndex
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherMetrics
import com.weather.vibe.domain.weather.usecase.ResolveTodaySunInfo
import com.weather.vibe.domain.weather.usecase.ResolveTodayTemperatureBounds
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Factory
internal class HomeStateFactory(
  private val buildDailyTemperatureRanges: BuildDailyTemperatureRanges,
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
    when (current is HomeUiState.Loaded) {
      true -> current.copy(briefing = briefing, playlist = playlist)
      false -> current
    }

  fun applyPlaylist(current: HomeUiState, playlist: PlaylistUiState): HomeUiState =
    when (current is HomeUiState.Loaded) {
      true -> current.copy(playlist = playlist)
      false -> current
    }

  fun applyDailyVibe(current: HomeUiState, state: DailyVibeUiState): HomeUiState =
    when (current is HomeUiState.Loaded) {
      true -> current.copy(dailyVibe = state)
      false -> current
    }

  fun createDailyVibe(vibe: DailyVibe): DailyVibeUiState =
    DailyVibeUiState(
      emoji = resources.dailyVibeEmoji(vibe.mood),
      headline = resources.dailyVibeHeadline(vibe.score, vibe.mood),
      oneLiner = resources.dailyVibeOneLiner(vibe.mood),
      contentDescription = resources.dailyVibeContentDescription(vibe.mood, vibe.score)
    )

  fun markGenreAsRejecting(current: HomeUiState, genre: String): HomeUiState {

    if (current !is HomeUiState.Loaded) return current
    val loadedPlaylist = current.playlist as? PlaylistUiState.Loaded
      ?: return current

    return current.copy(
      playlist = loadedPlaylist.copy(
        genres = loadedPlaylist.genres.map {
          if (it.name == genre) it.copy(isRejecting = true) else it
        }
      )
    )
  }

  fun areAllGenresRejected(current: HomeUiState): Boolean {
    val loaded = current as? HomeUiState.Loaded ?: return false
    val playlist = loaded.playlist as? PlaylistUiState.Loaded ?: return false
    return playlist.genres.all { it.isRejecting }
  }

  fun create(
    data: WeatherData,
    unit: TemperatureUnit = TemperatureUnit.CELSIUS
  ): HomeUiState.Loaded {

    val today = timeProvider.today()
    val currentHourIndex = findCurrentHourIndex(hours = data.hourlyForecast.map { it.time })
    val currentMetrics = getCurrentWeatherMetrics(data)
    val sunInfo = resolveTodaySunInfo(data.dailyForecast)

    return HomeUiState.Loaded(
      currentWeather = createCurrentWeather(data, unit),
      dailyForecast = createDailyForecast(data, unit, today),
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
    val loaded = current as? HomeUiState.Loaded ?: return current
    return create(data, unit).copy(
      briefing = loaded.briefing,
      dailyVibe = loaded.dailyVibe,
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

    val ranges = buildDailyTemperatureRanges(
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
    get() = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.getDefault())

  private val dayFormatter: DateTimeFormatter
    get() = DateTimeFormatter.ofPattern(DAY_FORMAT, Locale.getDefault())

  private companion object {

    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val TIME_OUTPUT_FORMAT = "HH:mm"

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter =
      DateTimeFormatter.ofPattern(TIME_OUTPUT_FORMAT)
  }
}
