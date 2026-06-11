package com.weather.vibe.domain.alerts.fake

import com.weather.vibe.domain.alerts.cache.AlertNotificationLog
import java.time.LocalDateTime

internal class FakeAlertNotificationLog : AlertNotificationLog {

  private val store: MutableMap<String, LocalDateTime> = mutableMapOf()

  override suspend fun lastNotified(): Map<String, LocalDateTime> = store.toMap()

  override suspend fun record(alertKey: String, expectedAt: LocalDateTime) {
    store[alertKey] = expectedAt
  }
}
