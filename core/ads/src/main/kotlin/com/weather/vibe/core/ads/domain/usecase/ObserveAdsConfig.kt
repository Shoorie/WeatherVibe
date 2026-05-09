package com.weather.vibe.core.ads.domain.usecase

import com.weather.vibe.core.ads.data.AdsConfigParser
import com.weather.vibe.core.ads.domain.config.AdsConfig
import com.weather.vibe.core.ads.domain.config.AdsConfigKeys
import com.weather.vibe.core.remoteconfig.domain.FeatureFlags
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
internal class ObserveAdsConfig(
  private val featureFlags: FeatureFlags,
  private val parser: AdsConfigParser
) {

  operator fun invoke(): Flow<AdsConfig> =
    featureFlags.updates
      .map { parser.parse(featureFlags.string(AdsConfigKeys.AdsConfigFlag)) }
      .distinctUntilChanged()
}
