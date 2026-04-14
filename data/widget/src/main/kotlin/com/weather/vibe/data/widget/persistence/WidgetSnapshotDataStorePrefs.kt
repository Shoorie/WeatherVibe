package com.weather.vibe.data.widget.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.weather.vibe.data.widget.persistence.WidgetSnapshotDataStorePrefs.Companion.FILE_NAME

private val Context.widgetSnapshotDataStore: DataStore<WidgetSnapshotCacheData> by dataStore(
  fileName = FILE_NAME,
  serializer = WidgetSnapshotCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    WidgetSnapshotCacheData.getDefaultInstance()
  }
)

internal class WidgetSnapshotDataStorePrefs {

  fun get(context: Context): DataStore<WidgetSnapshotCacheData> =
    context.widgetSnapshotDataStore

  companion object {
    const val FILE_NAME = "widget_snapshot_cache_prefs"
  }
}
