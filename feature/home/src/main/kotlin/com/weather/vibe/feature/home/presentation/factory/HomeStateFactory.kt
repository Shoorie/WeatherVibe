package com.weather.vibe.feature.home.presentation.factory

import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey
import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.vibe.model.DailyVibe
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.DailyTemperatureRange
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.model.WeatherVibeKey
import com.weather.vibe.feature.home.presentation.state.AirQualityPresentation
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.presentation.state.EnvironmentSectionUiState
import com.weather.vibe.feature.home.presentation.state.ForecastSectionUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.ui.HomeResources
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Factory
internal class HomeStateFactory(
  private val factories: HomeFactories,
  private val resources: HomeResources,
  private val temperature: TemperatureFormatter,
  private val timeProvider: TimeProvider,
  private val useCases: HomeFactoryUseCases
) {

  fun applyWeatherSuggestion(
    briefing: BriefingUiState,
    current: HomeUiState,
    playlist: PlaylistUiState
  ): HomeUiState =
    when (current is HomeUiState.Loaded) {
      true -> current.copy(
        aiSuggestion = current.aiSuggestion.copy(briefing = briefing, playlist = playlist)
      )
      false -> current
    }

  fun applyPlaylist(current: HomeUiState, playlist: PlaylistUiState): HomeUiState =
    when (current is HomeUiState.Loaded) {
      true -> current.copy(aiSuggestion = current.aiSuggestion.copy(playlist = playlist))
      false -> current
    }

  fun applyDailyVibe(current: HomeUiState, state: DailyVibeUiState): HomeUiState =
    when (current is HomeUiState.Loaded) {
      true -> current.copy(aiSuggestion = current.aiSuggestion.copy(dailyVibe = state))
      false -> current
    }

  fun applyAirQuality(
    current: HomeUiState,
    presentation: AirQualityPresentation
  ): HomeUiState =
    when (current is HomeUiState.Loaded) {
      true -> current.copy(
        environment = EnvironmentSectionUiState(
          airQualityChip = presentation.airQualityChip,
          alert = presentation.alert,
          pollenChip = presentation.pollenChip
        )
      )
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
    val loadedPlaylist = current.aiSuggestion.playlist as? PlaylistUiState.Loaded
      ?: return current

    return current.copy(
      aiSuggestion = current.aiSuggestion.copy(
        playlist = loadedPlaylist.copy(
          genres = loadedPlaylist.genres.map {
            if (it.name == genre) it.copy(isRejecting = true) else it
          }.toImmutableList()
        )
      )
    )
  }

  fun areAllGenresRejected(current: HomeUiState): Boolean {
    val loaded = current as? HomeUiState.Loaded ?: return false
    val playlist = loaded.aiSuggestion.playlist as? PlaylistUiState.Loaded ?: return false
    return playlist.genres.all { it.isRejecting }
  }

  fun create(
    data: WeatherData,
    unit: TemperatureUnit = TemperatureUnit.CELSIUS
  ): HomeUiState.Loaded {

    val today = timeProvider.today()
    val forecastHours = data.hourlyForecast.map { it.time }
    val currentHourIndex = useCases.findCurrentHourIndex(hours = forecastHours)
    val currentMetrics = useCases.getCurrentWeatherMetrics(data)
    val sunInfo = useCases.resolveTodaySunInfo(data.dailyForecast)

    return HomeUiState.Loaded(
      details = factories.metrics.create(currentMetrics, unit),
      forecast = ForecastSectionUiState(
        currentWeather = createCurrentWeather(data, unit),
        dailyForecast = createDailyForecast(data, unit, today),
        header = createHeader(data, today),
        hourlyForecast = createHourlyForecast(data.hourlyForecast, unit, currentHourIndex),
        sunriseSunset = factories.sunriseSunset.create(sunInfo)
      )
    )
  }

  fun createPlaylist(suggestion: WeatherSuggestion): PlaylistUiState.Loaded =
    factories.playlist.create(suggestion)

  fun createBriefing(suggestion: WeatherSuggestion): BriefingUiState.Loaded =
    BriefingUiState.Loaded(
      text = suggestion.briefText,
      outfit = suggestion.outfitSuggestion
    )

  fun createSharePoster(
    suggestion: WeatherSuggestion,
    vibeOneLiner: String?,
    weather: WeatherData,
    unit: TemperatureUnit
  ): SharePosterUiState {

    val vibeKey = useCases.resolveWeatherVibeKey(weather)

    return SharePosterUiState(
      cityName = weather.coordinates.name,
      conditionEmoji = weather.condition.emoji,
      conditionLabel = resources.conditionLabel(weather.condition),
      dateLabel = timeProvider.today().format(dateFormatter),
      gradientKey = VIBE_TO_GRADIENT.getValue(vibeKey),
      outfit = suggestion.outfitSuggestion,
      quoteText = vibeOneLiner ?: suggestion.briefText,
      temperature = weather.currentTemperature.formatted(unit),
      wordmarkHeadline = resources.shareWordmarkHeadline()
    )
  }

  fun reformatTemperatures(
    current: HomeUiState,
    data: WeatherData,
    unit: TemperatureUnit
  ): HomeUiState {
    val loaded = current as? HomeUiState.Loaded ?: return current
    return create(data, unit).copy(
      aiSuggestion = loaded.aiSuggestion,
      environment = loaded.environment
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

    val VIBE_TO_GRADIENT: Map<WeatherVibeKey, ShareGradientKey> = mapOf(
      WeatherVibeKey.SUNNY to ShareGradientKey.SUNNY,
      WeatherVibeKey.CLOUDY to ShareGradientKey.CLOUDY,
      WeatherVibeKey.RAINY to ShareGradientKey.RAINY,
      WeatherVibeKey.STORMY to ShareGradientKey.STORMY,
      WeatherVibeKey.SNOWY to ShareGradientKey.SNOWY,
      WeatherVibeKey.NIGHT to ShareGradientKey.NIGHT
    )
  }
}
