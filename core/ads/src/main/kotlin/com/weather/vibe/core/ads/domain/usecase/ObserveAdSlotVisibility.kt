package com.weather.vibe.core.ads.domain.usecase

import com.weather.vibe.core.ads.consent.ConsentManager
import com.weather.vibe.core.ads.domain.AdPlacement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.annotation.Factory

@Factory
internal class ObserveAdSlotVisibility(
  private val consentManager: ConsentManager,
  private val observeAdsConfig: ObserveAdsConfig
) {

  operator fun invoke(placement: AdPlacement): Flow<Boolean> =
    combine(observeAdsConfig(), consentManager.canRequestAds) { config, canRequestAds ->
      config.isPlacementEnabled(placement.key) && canRequestAds
    }.distinctUntilChanged()
}
