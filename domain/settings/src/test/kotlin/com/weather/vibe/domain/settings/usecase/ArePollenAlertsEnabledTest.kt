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

class ArePollenAlertsEnabledTest {

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val arePollenAlertsEnabled =
    ArePollenAlertsEnabled(observeUserSettings = observeUserSettings)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given pollen alerts toggled on, then pollen alerts enabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(pollenAlertsEnabled = true)))

    expectThat(arePollenAlertsEnabled()).isEqualTo(true)
  }

  @Test
  fun `given pollen alerts toggled off, then pollen alerts disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(pollenAlertsEnabled = false)))

    expectThat(arePollenAlertsEnabled()).isEqualTo(false)
  }

  @Test
  fun `given settings read fails, then pollen alerts disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(failure(RuntimeException("boom")))

    expectThat(arePollenAlertsEnabled()).isEqualTo(false)
  }
}
