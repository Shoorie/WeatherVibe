package com.weather.vibe.domain.alerts.dedupe

import com.weather.vibe.domain.alerts.model.WeatherAlert
import org.koin.core.annotation.Single
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Single
class AlertDeduplicator {

  private val lastNotified: MutableMap<String, LocalDateTime> = ConcurrentHashMap()

  fun filterFresh(alerts: List<WeatherAlert>): List<WeatherAlert> =
    alerts
      .filter(::isFresh)
      .onEach(::remember)

  private fun isFresh(alert: WeatherAlert): Boolean =
    lastNotified[alert.dedupeKey] != alert.expectedAtRounded

  private fun remember(alert: WeatherAlert) {
    lastNotified[alert.dedupeKey] = alert.expectedAtRounded
  }

  private val WeatherAlert.dedupeKey: String
    get() = this::class.simpleName.orEmpty()

  private val WeatherAlert.expectedAtRounded: LocalDateTime
    get() = expectedAt
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
}
