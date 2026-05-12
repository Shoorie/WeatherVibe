package com.weather.vibe.domain.ads.usecase

import com.weather.vibe.domain.ads.consent.AdConsentState
import com.weather.vibe.domain.ads.placement.AdPlacement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.annotation.Factory

@Factory
class ObserveAdSlotVisibility(
  private val consentState: AdConsentState,
  private val observeAdsConfig: ObserveAdsConfig
) {

  operator fun invoke(placement: AdPlacement): Flow<Boolean> =
    combine(
      observeAdsConfig(),
      consentState.canRequestAds
    ) { config, canRequestAds ->
      config.isPlacementEnabled(placement.key) && canRequestAds
    }.distinctUntilChanged()
}
