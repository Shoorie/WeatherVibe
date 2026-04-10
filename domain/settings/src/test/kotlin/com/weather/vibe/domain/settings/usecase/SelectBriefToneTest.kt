package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.testing.settings.fixture.FakeSettingsCache
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.DEFAULT_SETTINGS
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

class SelectBriefToneTest {

  private val cache = FakeSettingsCache(initial = DEFAULT_SETTINGS)
  private val selectBriefTone = SelectBriefTone(cache = cache)

  @Test
  fun `when tone selected, then cache holds chosen tone`() = runTest {

    selectBriefTone(tone = FORMAL)

    expectThat(cache.current.briefTone).isEqualTo(FORMAL)
  }

  @Test
  fun `given cache throws, when tone selected, then error propagates`() = runTest {

    cache.writeError = IllegalStateException("boom")

    expectThrows<IllegalStateException> { selectBriefTone(tone = FORMAL) }
  }
}
