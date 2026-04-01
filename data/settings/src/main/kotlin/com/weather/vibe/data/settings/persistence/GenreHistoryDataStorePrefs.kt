package com.weather.vibe.data.settings.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.weather.vibe.data.settings.persistence.GenreHistoryDataStorePrefs.Companion.FILE_NAME

private val Context.genreHistoryDataStore: DataStore<GenreHistoryCacheData> by dataStore(
  fileName = FILE_NAME,
  serializer = GenreHistoryCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    GenreHistoryCacheData.getDefaultInstance()
  }
)

internal class GenreHistoryDataStorePrefs {

  fun get(context: Context): DataStore<GenreHistoryCacheData> =
    context.genreHistoryDataStore

  companion object {
    const val FILE_NAME = "genre_history_cache_prefs"
  }
}
