package com.weather.vibe.core.remoteconfig.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_DEFAULT
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_REMOTE
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.weather.vibe.core.remoteconfig.data.RemoteConfigDefaults.MinimumFetchIntervalSeconds
import com.weather.vibe.core.remoteconfig.domain.FeatureFlags
import com.weather.vibe.core.remoteconfig.domain.flag.BooleanFeatureFlag
import com.weather.vibe.core.remoteconfig.domain.flag.StringFeatureFlag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.core.annotation.Single

@Single(binds = [FeatureFlags::class])
internal class DefaultFeatureFlags(
  private val remoteConfig: FirebaseRemoteConfig
) : FeatureFlags {

  private val _updates = MutableSharedFlow<Unit>(replay = 1)

  override val updates: Flow<Unit> = _updates.asSharedFlow()

  init {
    configureFetchSettings()
    fetchAndActivate()
  }

  override fun bool(flag: BooleanFeatureFlag): Boolean {
    val value = remoteConfig.getValue(flag.key)
    return when (value.source) {
      VALUE_SOURCE_REMOTE -> value.asBoolean()
      VALUE_SOURCE_DEFAULT -> value.asBoolean()
      else -> flag.default
    }
  }

  override fun string(flag: StringFeatureFlag): String {
    val value = remoteConfig.getValue(flag.key)
    return when (value.source) {
      VALUE_SOURCE_REMOTE -> value.asString()
      VALUE_SOURCE_DEFAULT -> value.asString()
      else -> flag.default
    }
  }

  private fun configureFetchSettings() {
    val settings = remoteConfigSettings {
      minimumFetchIntervalInSeconds = MinimumFetchIntervalSeconds
    }
    remoteConfig.setConfigSettingsAsync(settings)
  }

  private fun fetchAndActivate() {
    remoteConfig.fetchAndActivate().addOnCompleteListener { _updates.tryEmit(Unit) }
  }
}
