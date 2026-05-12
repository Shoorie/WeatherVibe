package com.weather.vibe.domain.ads.usecase

import com.weather.vibe.domain.ads.config.AdsConfig
import com.weather.vibe.domain.remoteconfig.RemoteConfigActivations
import com.weather.vibe.domain.remoteconfig.proxy.RemoteConfigProxy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveAdsConfig(
  private val activations: RemoteConfigActivations,
  private val proxy: RemoteConfigProxy,
  private val source: AdsConfigSource
) {

  operator fun invoke(): Flow<AdsConfig> =
    activations.activations
      .map { source.parse(proxy.getString(ADS_CONFIG_KEY)) }
      .distinctUntilChanged()

  private companion object {
    const val ADS_CONFIG_KEY = "ads_config"
  }
}
