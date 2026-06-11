package com.weather.vibe.data.alerts.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.alertDedupeDataStore: DataStore<AlertDedupeData> by dataStore(
  fileName = AlertDedupeDataStorePrefs.FILE_NAME,
  serializer = AlertDedupeCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    AlertDedupeData.getDefaultInstance()
  }
)

internal class AlertDedupeDataStorePrefs {

  fun get(context: Context): DataStore<AlertDedupeData> = context.alertDedupeDataStore

  companion object {
    const val FILE_NAME = "alert_dedupe_cache_prefs"
  }
}
