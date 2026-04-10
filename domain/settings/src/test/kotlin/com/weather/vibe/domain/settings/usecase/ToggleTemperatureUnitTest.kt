package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.testing.settings.fixture.FakeSettingsCache
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

class ToggleTemperatureUnitTest {

  @Test
  fun `given celsius, when toggled, then cache flips to fahrenheit`() = runTest {

    val after = toggleFrom(CELSIUS)

    expectThat(after).isEqualTo(FAHRENHEIT)
  }

  @Test
  fun `given fahrenheit, when toggled, then cache flips to celsius`() = runTest {

    val after = toggleFrom(FAHRENHEIT)

    expectThat(after).isEqualTo(CELSIUS)
  }

  @Test
  fun `given cache throws, when toggled, then error propagates`() = runTest {

    val cache = FakeSettingsCache().apply { writeError = IllegalStateException("boom") }

    expectThrows<IllegalStateException> {
      ToggleTemperatureUnit(cache = cache).invoke()
    }
  }

  private suspend fun toggleFrom(initial: TemperatureUnit): TemperatureUnit {
    val cache = FakeSettingsCache(initial = userSettings(temperatureUnit = initial))
    ToggleTemperatureUnit(cache = cache).invoke()
    return cache.current.temperatureUnit
  }
}
