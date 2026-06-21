package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.premium.cache.PremiumStateCache
import com.weather.vibe.domain.settings.model.BriefTone
import org.koin.core.annotation.Factory

@Factory
class UnlockToneTemporarily internal constructor(
  private val premiumStateCache: PremiumStateCache,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(tone: BriefTone) {
    val until = timeProvider.nowEpochMillis() + UNLOCK_DURATION_MILLIS
    premiumStateCache.update { state -> state.withToneUnlocked(tone, until) }
  }

  private companion object {
    const val UNLOCK_DURATION_MILLIS = 24L * 60L * 60L * 1000L
  }
}
