package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.TodaySunInfo
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState.Companion.Empty
import com.weather.vibe.feature.home.ui.HomeResources
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

@Factory
internal class SunriseSunsetStateFactory(
  private val resources: HomeResources
) {

  fun create(info: TodaySunInfo?): SunriseSunsetUiState {
    info ?: return Empty
    return SunriseSunsetUiState(
      dayLength = formatDayLength(info.dayLength),
      sunProgress = info.sunProgress,
      sunriseTime = formatTime(info.sunrise),
      sunsetTime = formatTime(info.sunset)
    )
  }

  private fun formatTime(time: LocalDateTime): String =
    time.format(TIME_OUTPUT_FORMATTER)

  private fun formatDayLength(duration: Duration): String =
    resources.dayLengthFormat(
      hours = duration.toHours().toInt(),
      minutes = (duration.toMinutes() % MINUTES_PER_HOUR).toInt()
    )

  private companion object {

    const val MINUTES_PER_HOUR = 60
    const val TIME_OUTPUT_FORMAT = "HH:mm"

    val TIME_OUTPUT_FORMATTER: DateTimeFormatter =
      ofPattern(TIME_OUTPUT_FORMAT)
  }
}
