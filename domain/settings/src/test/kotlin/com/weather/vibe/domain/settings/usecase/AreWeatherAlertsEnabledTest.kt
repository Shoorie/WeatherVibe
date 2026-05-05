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

class AreWeatherAlertsEnabledTest {

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val areWeatherAlertsEnabled =
    AreWeatherAlertsEnabled(observeUserSettings = observeUserSettings)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given weather alerts toggled on, then weather alerts enabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(weatherAlertsEnabled = true)))

    expectThat(areWeatherAlertsEnabled()).isEqualTo(true)
  }

  @Test
  fun `given weather alerts toggled off, then weather alerts disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(weatherAlertsEnabled = false)))

    expectThat(areWeatherAlertsEnabled()).isEqualTo(false)
  }

  @Test
  fun `given settings read fails, then weather alerts disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(failure(RuntimeException("boom")))

    expectThat(areWeatherAlertsEnabled()).isEqualTo(false)
  }
}
