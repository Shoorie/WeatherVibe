package com.weather.vibe.data.profile.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.profileDataStore: DataStore<ProfileCacheData> by dataStore(
  fileName = ProfileDataStorePrefs.FILE_NAME,
  serializer = ProfileCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    ProfileCacheData.getDefaultInstance()
  }
)

internal class ProfileDataStorePrefs {

  fun get(context: Context): DataStore<ProfileCacheData> = context.profileDataStore

  companion object {
    const val FILE_NAME = "profile_cache_prefs"
  }
}
