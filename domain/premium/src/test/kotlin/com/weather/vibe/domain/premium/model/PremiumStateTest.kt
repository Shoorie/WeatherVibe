package com.weather.vibe.domain.premium.model

import com.weather.vibe.domain.settings.model.BriefTone.COACH
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.isTrue

class PremiumStateTest {

  @Test
  fun `given tone unlocked until future, then tone is accessible`() {

    val state = PremiumState.NONE.withToneUnlocked(COACH, until = LATER)

    val accessible = state.accessibleUnlockedTones(now = NOW)

    expectThat(accessible).containsExactly(COACH)
  }

  @Test
  fun `given tone unlock expired, then tone not accessible`() {

    val state = PremiumState.NONE.withToneUnlocked(COACH, until = EARLIER)

    val accessible = state.accessibleUnlockedTones(now = NOW)

    expectThat(accessible).isEmpty()
  }

  @Test
  fun `when premium activated, then state is premium`() {

    val state = PremiumState.NONE.withPremium(active = true)

    expectThat(state.isPremium).isTrue()
  }

  private companion object {
    const val NOW = 1_000_000L
    const val LATER = 2_000_000L
    const val EARLIER = 500_000L
  }
}
