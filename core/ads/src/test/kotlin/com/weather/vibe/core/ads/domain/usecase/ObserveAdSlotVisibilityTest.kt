package com.weather.vibe.core.ads.domain.usecase

import app.cash.turbine.test
import com.weather.vibe.core.ads.consent.ConsentManager
import com.weather.vibe.core.ads.domain.AdPlacement
import com.weather.vibe.core.ads.fixture.AdsConfigFixtures
import com.weather.vibe.core.ads.fixture.AdsConfigFixtures.FULLY_ENABLED
import com.weather.vibe.core.ads.fixture.AdsConfigFixtures.adsConfig
import com.weather.vibe.core.ads.fixture.AdsConfigFixtures.configWith
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ObserveAdSlotVisibilityTest {

  private val consentManager = mockk<ConsentManager>()
  private val observeAdsConfig = mockk<ObserveAdsConfig>()
  private val observeAdSlotVisibility = ObserveAdSlotVisibility(
    consentManager = consentManager,
    observeAdsConfig = observeAdsConfig
  )

  @Before
  fun setUp() {
    every { consentManager.canRequestAds } returns flowOf(true)
    every { observeAdsConfig() } returns flowOf(FULLY_ENABLED)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given ads enabled and consent granted, when visibility observed, then emit true`() =
    runTest {
      observeAdSlotVisibility(AdPlacement.HomeBottom).test {
        expectThat(awaitItem()).isEqualTo(true)
        awaitComplete()
      }
    }

  @Test
  fun `given global ads disabled, when visibility observed, then emit false`() = runTest {
    every { observeAdsConfig() } returns flowOf(adsConfig(globalEnabled = false))

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `given placement disabled, when visibility observed, then emit false`() = runTest {
    every { observeAdsConfig() } returns flowOf(
      configWith(placement = AdPlacement.HomeBottom, enabled = AdsConfigFixtures.PLACEMENT_DISABLED)
    )

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `given placement missing in config, when visibility observed, then emit false`() = runTest {
    every { observeAdsConfig() } returns flowOf(adsConfig(globalEnabled = true))

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `given consent not granted, when visibility observed, then emit false`() = runTest {
    every { consentManager.canRequestAds } returns flowOf(false)

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }
}
