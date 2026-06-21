package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.domain.premium.fake.FakePremiumStateCache
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class SetPremiumActiveTest {

  private val cache = FakePremiumStateCache()
  private val setPremiumActive = SetPremiumActive(premiumStateCache = cache)

  @Test
  fun `when premium activated, then state is premium`() = runTest {
    setPremiumActive(active = true)

    expectThat(cache.current.isPremium).isTrue()
  }

  @Test
  fun `given premium active, when deactivated, then state is not premium`() = runTest {
    setPremiumActive(active = true)

    setPremiumActive(active = false)

    expectThat(cache.current.isPremium).isFalse()
  }
}
