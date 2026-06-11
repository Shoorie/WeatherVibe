package com.weather.vibe.domain.alerts.dedupe

import com.weather.vibe.domain.alerts.cache.AlertNotificationLog
import com.weather.vibe.domain.alerts.model.WeatherAlert
import org.koin.core.annotation.Single
import java.time.LocalDateTime

@Single
class AlertDeduplicator internal constructor(
  private val notificationLog: AlertNotificationLog
) {

  suspend fun filterFresh(alerts: List<WeatherAlert>): List<WeatherAlert> {
    val lastNotified = notificationLog.lastNotified()
    val fresh = alerts.filter { it.isFreshAgainst(lastNotified) }
    fresh.forEach { remember(it) }
    return fresh
  }

  private fun WeatherAlert.isFreshAgainst(lastNotified: Map<String, LocalDateTime>): Boolean =
    lastNotified[dedupeKey] != expectedAtRounded

  private suspend fun remember(alert: WeatherAlert) {
    notificationLog.record(alertKey = alert.dedupeKey, expectedAt = alert.expectedAtRounded)
  }

  private val WeatherAlert.dedupeKey: String
    get() = this::class.simpleName.orEmpty()

  private val WeatherAlert.expectedAtRounded: LocalDateTime
    get() = expectedAt
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
}
