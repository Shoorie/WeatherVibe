package com.weather.vibe.data.alerts.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.alerts.cache.AlertNotificationLog
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import java.time.LocalDateTime

@Single(binds = [AlertNotificationLog::class])
internal class DefaultAlertNotificationLog(
  @param:AlertDedupeQualifier
  private val dataStore: DataStore<AlertDedupeData>
) : AlertNotificationLog {

  override suspend fun lastNotified(): Map<String, LocalDateTime> =
    dataStore.data.first()
      .lastNotifiedMap
      .mapValues { LocalDateTime.parse(it.value) }

  override suspend fun record(alertKey: String, expectedAt: LocalDateTime) {
    dataStore.updateData {
      it.toBuilder()
        .putLastNotified(alertKey, expectedAt.toString())
        .build()
    }
  }
}
