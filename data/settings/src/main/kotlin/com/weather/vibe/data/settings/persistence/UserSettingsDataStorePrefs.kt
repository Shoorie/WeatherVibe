package com.weather.vibe.data.settings.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.weather.vibe.data.settings.persistence.UserSettingsDataStorePrefs.Companion.FILE_NAME

private val Context.userSettingsDataStore: DataStore<UserSettingsCacheData> by dataStore(
  fileName = FILE_NAME,
  serializer = UserSettingsCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    UserSettingsCacheData.getDefaultInstance()
  }
)

internal class UserSettingsDataStorePrefs {

  fun get(context: Context): DataStore<UserSettingsCacheData> =
    context.userSettingsDataStore

  companion object {
    const val FILE_NAME = "user_settings_cache_prefs"
  }
}

