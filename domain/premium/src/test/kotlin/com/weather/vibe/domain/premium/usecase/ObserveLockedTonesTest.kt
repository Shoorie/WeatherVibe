package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.premium.fake.FakePremiumStateCache
import com.weather.vibe.domain.settings.model.BriefTone.CINEMATIC
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.CYNIC
import com.weather.vibe.domain.settings.model.BriefTone.RPG
import com.weather.vibe.domain.settings.model.BriefTone.SCI_FI
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.doesNotContain
import strikt.assertions.isEmpty
import strikt.assertions.isFailure

class ObserveLockedTonesTest {

  private val timeProvider = mockk<TimeProvider>()
  private val cache = FakePremiumStateCache()
  private val observeLockedTones = ObserveLockedTones(
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
  fun `given free user, then all premium tones are locked`() = runTest {
    val locked = observeLockedTones().first().getOrThrow()

    expectThat(locked).containsExactlyInAnyOrder(COACH, SCI_FI, RPG, CINEMATIC, CYNIC)
  }

  @Test
  fun `given premium user, then no tones are locked`() = runTest {
    cache.update { it.withPremium(active = true) }

    val locked = observeLockedTones().first().getOrThrow()

    expectThat(locked).isEmpty()
  }

  @Test
  fun `given premium tone temporarily unlocked, then that tone is not locked`() = runTest {
    cache.update { it.withToneUnlocked(COACH, untilEpochMillis = LATER) }

    val locked = observeLockedTones().first().getOrThrow()

    expectThat(locked).doesNotContain(COACH)
  }

  @Test
  fun `given read fails, then locked tones emit failure`() = runTest {
    cache.readError = RuntimeException(MESSAGE)

    val result = observeLockedTones().first()

    expectThat(result).isFailure()
  }

  private companion object {
    const val NOW = 1_000_000L
    const val LATER = 2_000_000L
    const val MESSAGE = "cache read failed"
  }
}
