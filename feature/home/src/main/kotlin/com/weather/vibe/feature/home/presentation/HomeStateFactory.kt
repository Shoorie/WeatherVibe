package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.MoodPlaylist
import com.weather.vibe.domain.weather.model.WeatherData
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
import kotlin.math.roundToInt

@Factory
internal class HomeStateFactory(
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

  fun create(data: WeatherData): Loaded =
    Loaded(
      currentWeather = createCurrentWeather(data),
      dailyForecast = createDailyForecast(data.dailyForecast),
      detailsSections = metricsFactory.create(data),
      header = createHeader(data),
      hourlyForecast = createHourlyForecast(data.hourlyForecast),
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
    if (sunrise == null || sunset == null) return 0f
    val now = LocalDateTime.now()
    val dayMinutes = Duration.between(sunrise, sunset).toMinutes().toFloat()
    val elapsed = Duration.between(sunrise, now).toMinutes().toFloat()
    return (elapsed / dayMinutes).coerceIn(minimumValue = 0f, maximumValue = 1f)
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

  private fun formatTemperature(value: Double): String =
    "${value.roundToInt()}$DEGREE_SYMBOL"

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

    const val DATE_FORMAT = "EEEE, d MMMM"
    const val SPOTIFY_SCHEME = "spotify:search:"
    const val YT_MUSIC_BASE_URL = "https://music.youtube.com/search?q="
    const val DAY_FORMAT = "EEE"
    const val DEGREE_SYMBOL = "°"
    const val MINUTES_PER_HOUR = 60
    const val TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val TIME_OUTPUT_FORMAT = "HH:mm"

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
