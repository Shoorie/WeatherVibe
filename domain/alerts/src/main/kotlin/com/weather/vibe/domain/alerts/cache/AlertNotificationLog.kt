package com.weather.vibe.domain.alerts.cache

import java.time.LocalDateTime

interface AlertNotificationLog {
  suspend fun lastNotified(): Map<String, LocalDateTime>
  suspend fun record(alertKey: String, expectedAt: LocalDateTime)
}
