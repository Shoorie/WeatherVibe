package com.weather.vibe.domain.premium.cache

import com.weather.vibe.domain.premium.model.PremiumState
import kotlinx.coroutines.flow.Flow

interface PremiumStateCache {
  fun observe(): Flow<PremiumState>
  suspend fun update(change: (PremiumState) -> PremiumState)
}
