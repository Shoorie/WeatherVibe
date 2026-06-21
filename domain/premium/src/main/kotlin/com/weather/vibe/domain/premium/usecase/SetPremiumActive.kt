package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.domain.premium.cache.PremiumStateCache
import org.koin.core.annotation.Factory

@Factory
class SetPremiumActive internal constructor(
  private val premiumStateCache: PremiumStateCache
) {

  suspend operator fun invoke(active: Boolean) {
    premiumStateCache.update { state -> state.withPremium(active) }
  }
}
