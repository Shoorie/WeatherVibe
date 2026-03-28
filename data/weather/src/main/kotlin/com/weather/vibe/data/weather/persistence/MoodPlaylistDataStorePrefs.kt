package com.weather.vibe.data.weather.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.moodPlaylistDataStore: DataStore<MoodPlaylistCacheData> by dataStore(
  fileName = MoodPlaylistDataStorePrefs.FILE_NAME,
  serializer = MoodPlaylistCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    MoodPlaylistCacheData.getDefaultInstance()
  }
)

internal class MoodPlaylistDataStorePrefs {

  fun get(context: Context): DataStore<MoodPlaylistCacheData> = context.moodPlaylistDataStore

  companion object {
    const val FILE_NAME = "mood_playlist_cache_prefs"
  }
}
