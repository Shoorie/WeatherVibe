package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.MoodPlaylist
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.ConvertTemperature
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Factory
internal class HomeStateFactory(
  private val convertTemperature: ConvertTemperature,
  private val metricsFactory: MetricsStateFactory,
  private val resources: HomeResources
) {

  fun applyAiContent(
    briefing: BriefingUiState,
    current: HomeUiState,
    playlist: PlaylistUiState
  ): HomeUiState =
    when (current is Loaded) {
      true -> current.copy(briefing = briefing, playlist = playlist)
      false -> current
    }

  fun createPlaylist(data: MoodPlaylist): PlaylistUiState.Loaded {

    val spotifyQuery = data.genres.joinToString(separator = " ")
    val ytQuery = data.genres.joinToString(separator = "+")

    return PlaylistUiState.Loaded(
      genres = data.genres,
      mood = data.mood,
      spotifyQuery = "$SPOTIFY_SCHEME$spotifyQuery",
      ytMusicUrl = "$YT_MUSIC_BASE_URL$ytQuery"
    )
  }

  fun create(data: WeatherData, temperatureUnit: TemperatureUnit = CELSIUS): Loaded =
    Loaded(
      currentWeather = createCurrentWeather(data, temperatureUnit),
      dailyForecast = createDailyForecast(data.dailyForecast, temperatureUnit),
      detailsSections = metricsFactory.create(data, temperatureUnit),
      header = createHeader(data),
      hourlyForecast = createHourlyForecast(data.hourlyForecast, temperatureUnit),
      sunriseSunset = createSunriseSunset(data.dailyForecast)
    )

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
      conditionLabel = data.condition.label,
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

  private fun createSunriseSunset(
    days: List<DailyWeather>
  ): SunriseSunsetUiState {
    val today = days.firstOrNull()
    val sunriseTime = today?.sunrise?.parseDateTime()
    val sunsetTime = today?.sunset?.parseDateTime()
    return SunriseSunsetUiState(
      dayLength = formatDayLength(sunriseTime, sunsetTime),
      sunProgress = calculateSunProgress(sunriseTime, sunsetTime),
      sunriseTime = formatSunTime(today?.sunrise),
      sunsetTime = formatSunTime(today?.sunset)
    )
  }

  private fun String.parseDateTime(): LocalDateTime? =
    runCatching { LocalDateTime.parse(this, TIME_INPUT_FORMATTER) }.getOrNull()

  private fun calculateSunProgress(
    sunrise: LocalDateTime?,
    sunset: LocalDateTime?
  ): Float {
    if (sunrise == null || sunset == null) return MIN_PROGRESS
    val now = LocalDateTime.now()
    val dayMinutes = Duration.between(sunrise, sunset).toMinutes().toFloat()
    val elapsed = Duration.between(sunrise, now).toMinutes().toFloat()
    return (elapsed / dayMinutes).coerceIn(
      minimumValue = MIN_PROGRESS,
      maximumValue = MAX_PROGRESS
    )
  }

  private fun formatDayLength(
    sunrise: LocalDateTime?,
    sunset: LocalDateTime?
  ): String {
    if (sunrise == null || sunset == null) return ""
    val duration = Duration.between(sunrise, sunset)
    return resources.dayLengthFormat(
      hours = duration.toHours().toInt(),
      minutes = (duration.toMinutes() % MINUTES_PER_HOUR).toInt()
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

  private fun formatSunTime(isoTime: String?): String {
    if (isoTime.isNullOrEmpty()) return ""
    return runCatching {
      LocalDateTime
        .parse(isoTime, TIME_INPUT_FORMATTER)
        .format(TIME_OUTPUT_FORMATTER)
    }.getOrDefault(isoTime)
  }

  private companion object {

    const val CURRENT_HOUR_INDEX = 0
    const val DATE_FORMAT = "EEEE, d MMMM"
    const val DAY_FORMAT = "EEE"
    const val MAX_PROGRESS = 1f
    const val MIN_PROGRESS = 0f
    const val MINUTES_PER_HOUR = 60
    const val SPOTIFY_SCHEME = "spotify:search:"
    const val TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val TIME_OUTPUT_FORMAT = "HH:mm"
    const val YT_MUSIC_BASE_URL = "https://music.youtube.com/search?q="

    val DATE_FORMATTER: DateTimeFormatter? =
      ofPattern(DATE_FORMAT, Locale.ENGLISH)

    val DAY_FORMATTER: DateTimeFormatter? =
      ofPattern(DAY_FORMAT, Locale.ENGLISH)

    val TIME_INPUT_FORMATTER: DateTimeFormatter? =
      ofPattern(TIME_INPUT_FORMAT)

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter? =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
