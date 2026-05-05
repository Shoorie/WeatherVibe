package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class IsMoodReminderEnabledTest {

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val isMoodReminderEnabled =
    IsMoodReminderEnabled(observeUserSettings = observeUserSettings)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given mood reminder toggled on, then mood reminder enabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(moodReminderEnabled = true)))

    expectThat(isMoodReminderEnabled()).isEqualTo(true)
  }

  @Test
  fun `given mood reminder toggled off, then mood reminder disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(moodReminderEnabled = false)))

    expectThat(isMoodReminderEnabled()).isEqualTo(false)
  }

  @Test
  fun `given settings read fails, then mood reminder disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(failure(RuntimeException("boom")))

    expectThat(isMoodReminderEnabled()).isEqualTo(false)
  }
}
