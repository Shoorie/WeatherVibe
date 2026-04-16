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

class AreAlertsEnabledTest {

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val areAlertsEnabled = AreAlertsEnabled(observeUserSettings = observeUserSettings)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given alerts toggled on, when invoked, then alerts enabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(alertsEnabled = true)))

    expectThat(areAlertsEnabled()).isEqualTo(true)
  }

  @Test
  fun `given alerts toggled off, when invoked, then alerts disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(alertsEnabled = false)))

    expectThat(areAlertsEnabled()).isEqualTo(false)
  }

  @Test
  fun `given settings read fails, when invoked, then alerts disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(failure(RuntimeException("boom")))

    expectThat(areAlertsEnabled()).isEqualTo(false)
  }
}
