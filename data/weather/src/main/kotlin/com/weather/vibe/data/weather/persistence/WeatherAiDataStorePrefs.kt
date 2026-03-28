package com.weather.vibe.data.weather.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.weatherAiDataStore: DataStore<WeatherAiCacheData> by dataStore(
  fileName = WeatherAiDataStorePrefs.FILE_NAME,
  serializer = WeatherAiCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    WeatherAiCacheData.getDefaultInstance()
  }
)

internal class WeatherAiDataStorePrefs {

  fun get(context: Context): DataStore<WeatherAiCacheData> =
    context.weatherAiDataStore

  companion object {
    const val FILE_NAME = "weather_ai_cache_prefs"
  }
}
