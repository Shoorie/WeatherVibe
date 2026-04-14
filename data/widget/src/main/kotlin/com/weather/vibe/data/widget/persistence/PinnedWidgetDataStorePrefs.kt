package com.weather.vibe.data.widget.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.weather.vibe.data.widget.persistence.PinnedWidgetDataStorePrefs.Companion.FILE_NAME

private val Context.pinnedWidgetDataStore: DataStore<PinnedWidgetCacheData> by dataStore(
  fileName = FILE_NAME,
  serializer = PinnedWidgetCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    PinnedWidgetCacheData.getDefaultInstance()
  }
)

internal class PinnedWidgetDataStorePrefs {

  fun get(context: Context): DataStore<PinnedWidgetCacheData> =
    context.pinnedWidgetDataStore

  companion object {
    const val FILE_NAME = "pinned_widget_cache_prefs"
  }
}
