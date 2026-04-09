package com.weather.vibe.feature.home.presentation

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

@Factory
internal class SunriseSunsetStateFactory(
  private val resources: HomeResources,
  private val timeProvider: TimeProvider
) {

  fun create(days: List<DailyWeather>): SunriseSunsetUiState {
    val now = timeProvider.now()
    val today = days.firstOrNull()
    val sunriseTime = today?.sunrise?.parseDateTime()
    val sunsetTime = today?.sunset?.parseDateTime()
    return SunriseSunsetUiState(
      dayLength = formatDayLength(sunriseTime, sunsetTime),
      sunProgress = calculateSunProgress(sunriseTime, sunsetTime, now),
      sunriseTime = formatSunTime(today?.sunrise),
      sunsetTime = formatSunTime(today?.sunset)
    )
  }

  private fun String.parseDateTime(): LocalDateTime? =
    runCatching { LocalDateTime.parse(this, TIME_INPUT_FORMATTER) }.getOrNull()

  private fun calculateSunProgress(
    sunrise: LocalDateTime?,
    sunset: LocalDateTime?,
    now: LocalDateTime
  ): Float {
    if (sunrise == null || sunset == null) return MIN_PROGRESS
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

  private fun formatSunTime(isoTime: String?): String {
    if (isoTime.isNullOrEmpty()) return ""
    return runCatching {
      LocalDateTime
        .parse(isoTime, TIME_INPUT_FORMATTER)
        .format(TIME_OUTPUT_FORMATTER)
    }.getOrDefault(isoTime)
  }

  private companion object {

    const val MAX_PROGRESS = 1f
    const val MIN_PROGRESS = 0f
    const val MINUTES_PER_HOUR = 60
    const val TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm"
    const val TIME_OUTPUT_FORMAT = "HH:mm"

    val TIME_INPUT_FORMATTER: DateTimeFormatter? =
      ofPattern(TIME_INPUT_FORMAT)

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter? =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
