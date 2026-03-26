package com.weather.vibe.data.weather.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.briefingDataStore: DataStore<BriefingCacheData> by dataStore(
  fileName = BriefingDataStorePrefs.FILE_NAME,
  serializer = BriefingCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    BriefingCacheData.getDefaultInstance()
  }
)

internal class BriefingDataStorePrefs {

  fun get(context: Context): DataStore<BriefingCacheData> = context.briefingDataStore

  companion object {
    const val FILE_NAME = "briefing_cache_prefs"
  }
}
