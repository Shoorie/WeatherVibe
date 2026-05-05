package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.DEFAULT_SETTINGS
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class EnableDefaultNotificationsTest {

  private val cache = mockk<SettingsCache>()
  private val enableDefaults = EnableDefaultNotifications(cache = cache)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when defaults applied, then morning brief enabled`() = runTest {

    val captured = captureUpdated()

    enableDefaults()

    expectThat(captured().morningBriefEnabled).isTrue()
  }

  @Test
  fun `when defaults applied, then weather alerts enabled`() = runTest {

    val captured = captureUpdated()

    enableDefaults()

    expectThat(captured().weatherAlertsEnabled).isTrue()
  }

  @Test
  fun `when defaults applied, then mood reminder enabled`() = runTest {

    val captured = captureUpdated()

    enableDefaults()

    expectThat(captured().moodReminderEnabled).isTrue()
  }

  @Test
  fun `when defaults applied, then pollen alerts remain disabled`() = runTest {

    val captured = captureUpdated()

    enableDefaults()

    expectThat(captured().pollenAlertsEnabled).isFalse()
  }

  @Test
  fun `when defaults applied, then cache updated once`() = runTest {

    coJustRun { cache.update(any()) }

    enableDefaults()

    coVerify(exactly = 1) { cache.update(any()) }
  }

  private fun captureUpdated(): () -> UserSettings {
    val change = slot<(UserSettings) -> UserSettings>()
    coJustRun { cache.update(capture(change)) }
    return { change.captured(DEFAULT_SETTINGS) }
  }
}
