package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.premium.cache.PremiumStateCache
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class CanGenerateBrief internal constructor(
  private val observeUserSettings: ObserveUserSettings,
  private val premiumStateCache: PremiumStateCache,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(): Boolean {
    val state = premiumStateCache.observe().first()
    if (state.isPremium) return true
    val tone = observeUserSettings().first().getOrNull()?.briefTone ?: WITTY_AND_FRIENDLY
    if (!tone.isPremium) return true
    return tone in state.accessibleUnlockedTones(timeProvider.nowEpochMillis())
  }
}
