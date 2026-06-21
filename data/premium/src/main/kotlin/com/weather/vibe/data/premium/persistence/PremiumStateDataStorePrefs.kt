package com.weather.vibe.data.premium.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.weather.vibe.data.premium.persistence.PremiumStateDataStorePrefs.Companion.FILE_NAME

private val Context.premiumStateDataStore: DataStore<PremiumStateCacheData> by dataStore(
  fileName = FILE_NAME,
  serializer = PremiumStateCacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {
    PremiumStateCacheData.getDefaultInstance()
  }
)

internal class PremiumStateDataStorePrefs {

  fun get(context: Context): DataStore<PremiumStateCacheData> =
    context.premiumStateDataStore

  companion object {
    const val FILE_NAME = "premium_state_cache_prefs"
  }
}
