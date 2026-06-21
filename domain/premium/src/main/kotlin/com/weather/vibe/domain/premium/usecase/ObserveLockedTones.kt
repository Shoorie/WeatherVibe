package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.premium.cache.PremiumStateCache
import com.weather.vibe.domain.premium.model.PremiumState
import com.weather.vibe.domain.settings.model.BriefTone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class ObserveLockedTones internal constructor(
  private val premiumStateCache: PremiumStateCache,
  private val timeProvider: TimeProvider
) {

  operator fun invoke(): Flow<Result<Set<BriefTone>>> =
    premiumStateCache.observe()
      .map { state -> success(lockedTones(state)) }
      .catch { emit(failure(it)) }

  private fun lockedTones(state: PremiumState): Set<BriefTone> {
    if (state.isPremium) return emptySet()
    val unlocked = state.accessibleUnlockedTones(timeProvider.nowEpochMillis())
    return BriefTone.entries
      .filter { it.isPremium && it !in unlocked }
      .toSet()
  }
}
