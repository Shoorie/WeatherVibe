package com.weather.vibe.domain.premium.model

import com.weather.vibe.domain.settings.model.BriefTone

data class PremiumState(
  val isPremium: Boolean,
  val unlockedTones: Map<BriefTone, Long>
) {

  fun accessibleUnlockedTones(nowEpochMillis: Long): Set<BriefTone> =
    unlockedTones
      .filterValues { expiry -> expiry > nowEpochMillis }
      .keys

  fun withPremium(active: Boolean): PremiumState =
    copy(isPremium = active)

  fun withToneUnlocked(tone: BriefTone, untilEpochMillis: Long): PremiumState =
    copy(unlockedTones = unlockedTones + (tone to untilEpochMillis))

  companion object {
    val NONE = PremiumState(
      isPremium = false,
      unlockedTones = emptyMap()
    )
  }
}
