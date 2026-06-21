package com.weather.vibe.data.premium.persistence.mapper

import com.weather.vibe.data.premium.persistence.PremiumStateCacheData
import com.weather.vibe.domain.premium.model.PremiumState
import com.weather.vibe.domain.settings.model.BriefTone
import org.koin.core.annotation.Factory

@Factory
internal class PremiumStateCacheMapper {

  fun toDomain(cacheData: PremiumStateCacheData): PremiumState =
    PremiumState(
      isPremium = cacheData.isPremium,
      unlockedTones = cacheData.unlockedTonesMap.toUnlockedTones()
    )

  fun toCache(
    previous: PremiumStateCacheData,
    state: PremiumState
  ): PremiumStateCacheData =
    previous.toBuilder()
      .setIsPremium(state.isPremium)
      .clearUnlockedTones()
      .putAllUnlockedTones(state.unlockedTones.toCacheMap())
      .build()

  private fun Map<String, Long>.toUnlockedTones(): Map<BriefTone, Long> =
    mapNotNull { (name, expiry) -> name.toBriefTone()?.let { it to expiry } }
      .toMap()

  private fun Map<BriefTone, Long>.toCacheMap(): Map<String, Long> =
    mapKeys { (tone, _) -> tone.name }

  private fun String.toBriefTone(): BriefTone? =
    BriefTone.entries.firstOrNull { it.name == this }
}
