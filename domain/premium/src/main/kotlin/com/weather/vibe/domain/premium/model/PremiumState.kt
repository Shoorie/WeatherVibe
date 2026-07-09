package com.weather.vibe.domain.premium.model

import com.weather.vibe.domain.settings.model.BriefTone

data class PremiumState(
  val isPremium: Boolean,
  val unlockedTones: Map<BriefTone, Long>
) {

  fun accessibleUnlockedTones(now: Long): Set<BriefTone> =
    unlockedTones
      .filterValues { unlockedUntil -> unlockedUntil > now }
      .keys

  fun withPremium(active: Boolean): PremiumState =
    copy(isPremium = active)

  fun withToneUnlocked(tone: BriefTone, until: Long): PremiumState =
    copy(unlockedTones = unlockedTones + (tone to until))

  companion object {
    val NONE = PremiumState(
      isPremium = false,
      unlockedTones = emptyMap()
    )
  }
}
