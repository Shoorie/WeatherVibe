package com.weather.vibe.data.appearance.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.appearanceDataStore: DataStore<AppearanceCacheData> by dataStore(
  fileName = AppearanceDataStorePrefs.FILE_NAME,
  serializer = AppearanceCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    AppearanceCacheData.getDefaultInstance()
  }
)

internal class AppearanceDataStorePrefs {

  fun get(context: Context): DataStore<AppearanceCacheData> =
    context.appearanceDataStore

  companion object {
    const val FILE_NAME = "appearance_cache_prefs"
  }
}
