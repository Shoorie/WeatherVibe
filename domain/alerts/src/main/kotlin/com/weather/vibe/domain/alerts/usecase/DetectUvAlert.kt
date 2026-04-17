package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.model.WeatherAlert.HighUvIndex
import com.weather.vibe.domain.weather.model.UvLevel
import com.weather.vibe.domain.weather.model.UvLevel.HIGH
import com.weather.vibe.domain.weather.model.WeatherData
import org.koin.core.annotation.Factory
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Factory
internal class DetectUvAlert {

  operator fun invoke(weather: WeatherData): HighUvIndex? {

    val today = weather.dailyForecast.firstOrNull() ?: return null
    val level = UvLevel.from(today.uvIndexMax)
    if (level.ordinal < ALERT_THRESHOLD.ordinal) return null

    return HighUvIndex(
      expectedAt = expectedAt(today.date.atStartOfDay()),
      uvIndex = today.uvIndexMax.roundToInt(),
      level = level
    )
  }

  private fun expectedAt(dayStart: LocalDateTime): LocalDateTime =
    dayStart.withHour(PEAK_HOUR)

  private companion object {
    val ALERT_THRESHOLD: UvLevel = HIGH
    const val PEAK_HOUR = 13
  }
}
