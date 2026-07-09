package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.premium.fake.FakePremiumStateCache
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isTrue

class UnlockToneTemporarilyTest {

  private val timeProvider = mockk<TimeProvider>()
  private val cache = FakePremiumStateCache()
  private val unlockToneTemporarily = UnlockToneTemporarily(
    premiumStateCache = cache,
    timeProvider = timeProvider
  )

  @Before
  fun setUp() {
    every { timeProvider.nowEpochMillis() } returns NOW
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when tone unlocked, then tone accessible right after unlock`() = runTest {
    unlockToneTemporarily(COACH)

    expectThat(cache.current.accessibleUnlockedTones(now = NOW)).contains(COACH)
  }

  @Test
  fun `when tone unlocked, then access expires after one day`() = runTest {
    unlockToneTemporarily(COACH)

    val accessible = cache.current.accessibleUnlockedTones(now = NOW + DAY_MILLIS)

    expectThat(accessible.isEmpty()).isTrue()
  }

  private companion object {
    const val NOW = 1_000_000L
    const val DAY_MILLIS = 24L * 60L * 60L * 1000L
  }
}
