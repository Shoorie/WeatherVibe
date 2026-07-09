package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.premium.fake.FakePremiumStateCache
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import kotlin.Result.Companion.success

class CanGenerateBriefTest {

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val cache = FakePremiumStateCache()
  private val timeProvider = mockk<TimeProvider>()
  private val canGenerateBrief = CanGenerateBrief(
    observeUserSettings = observeUserSettings,
    premiumStateCache = cache,
    timeProvider = timeProvider
  )

  @Before
  fun setUp() {
    every { timeProvider.nowEpochMillis() } returns NOW
    selectTone(WITTY_AND_FRIENDLY)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given premium active, then brief can be generated`() = runTest {
    cache.update { it.withPremium(active = true) }
    selectTone(COACH)

    expectThat(canGenerateBrief()).isTrue()
  }

  @Test
  fun `given free tone selected, then brief can be generated`() = runTest {
    selectTone(WITTY_AND_FRIENDLY)

    expectThat(canGenerateBrief()).isTrue()
  }

  @Test
  fun `given locked premium tone selected, then brief cannot be generated`() = runTest {
    selectTone(COACH)

    expectThat(canGenerateBrief()).isFalse()
  }

  @Test
  fun `given premium tone unlocked, then brief can be generated`() = runTest {
    cache.update { it.withToneUnlocked(COACH, until = LATER) }
    selectTone(COACH)

    expectThat(canGenerateBrief()).isTrue()
  }

  private fun selectTone(tone: BriefTone) {
    val settings = mockk<UserSettings> { every { briefTone } returns tone }
    every { observeUserSettings() } returns flowOf(success(settings))
  }

  private companion object {
    const val NOW = 1_000_000L
    const val LATER = 2_000_000L
  }
}
