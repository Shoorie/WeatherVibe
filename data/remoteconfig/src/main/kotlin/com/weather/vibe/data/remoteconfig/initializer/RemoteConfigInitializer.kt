package com.weather.vibe.data.remoteconfig.initializer

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.weather.vibe.data.remoteconfig.R
import com.weather.vibe.domain.remoteconfig.RemoteConfigActivations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.core.annotation.Single

@Single(binds = [RemoteConfigActivations::class])
class RemoteConfigInitializer(
  private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigActivations {

  private val _activations = MutableSharedFlow<Unit>(replay = 1)

  override val activations: Flow<Unit> = _activations.asSharedFlow()

  fun start() {
    applyFetchSettings()
    applyDefaults()
    fetchAndActivate()
  }

  private fun applyFetchSettings() {
    val settings = remoteConfigSettings {
      minimumFetchIntervalInSeconds = MINIMUM_FETCH_INTERVAL_SECONDS
    }
    remoteConfig.setConfigSettingsAsync(settings)
  }

  private fun applyDefaults() {
    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
  }

  private fun fetchAndActivate() {
    remoteConfig
      .fetchAndActivate()
      .addOnCompleteListener { _activations.tryEmit(Unit) }
  }

  private companion object {
    const val MINIMUM_FETCH_INTERVAL_SECONDS = 3600L
  }
}
