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

class IsMorningBriefEnabledTest {

  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val isMorningBriefEnabled = IsMorningBriefEnabled(
    observeUserSettings = observeUserSettings
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given brief toggled on, when invoked, then brief enabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(morningBriefEnabled = true)))

    expectThat(isMorningBriefEnabled()).isEqualTo(true)
  }

  @Test
  fun `given brief toggled off, when invoked, then brief disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(morningBriefEnabled = false)))

    expectThat(isMorningBriefEnabled()).isEqualTo(false)
  }

  @Test
  fun `given settings read fails, when invoked, then brief disabled`() = runTest {

    every { observeUserSettings() } returns
      flowOf(failure(RuntimeException("boom")))

    expectThat(isMorningBriefEnabled()).isEqualTo(false)
  }
}
