package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.domain.premium.fake.FakePremiumStateCache
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class ObservePremiumStatusTest {

  private val cache = FakePremiumStateCache()
  private val observePremiumStatus = ObservePremiumStatus(premiumStateCache = cache)

  @Test
  fun `given premium active, then status emits true`() = runTest {
    cache.update { it.withPremium(active = true) }

    val result = observePremiumStatus().first()

    expectThat(result.getOrThrow()).isTrue()
  }

  @Test
  fun `given read fails, then status emits failure`() = runTest {
    cache.readError = RuntimeException(MESSAGE)

    val result = observePremiumStatus().first()

    expectThat(result.isFailure).isTrue()
  }

  private companion object {
    const val MESSAGE = "cache read failed"
  }
}
