package com.weather.vibe.domain.ads.usecase

import app.cash.turbine.test
import com.weather.vibe.domain.ads.consent.AdConsentState
import com.weather.vibe.domain.ads.fixture.AdsConfigFixtures.FULLY_ENABLED
import com.weather.vibe.domain.ads.fixture.AdsConfigFixtures.PLACEMENT_DISABLED
import com.weather.vibe.domain.ads.fixture.AdsConfigFixtures.adsConfig
import com.weather.vibe.domain.ads.fixture.AdsConfigFixtures.configWith
import com.weather.vibe.domain.ads.placement.AdPlacement.HomeBottom
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

  private val consentState = mockk<AdConsentState>()
  private val observeAdsConfig = mockk<ObserveAdsConfig>()
  private val observeAdSlotVisibility = ObserveAdSlotVisibility(
    consentState = consentState,
    observeAdsConfig = observeAdsConfig
  )

  @Before
  fun setUp() {
    every { consentState.canRequestAds } returns flowOf(true)
    every { observeAdsConfig() } returns flowOf(FULLY_ENABLED)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given ads enabled and consent granted, then emit true`() = runTest {
    observeAdSlotVisibility(HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(true)
      awaitComplete()
    }
  }

  @Test
  fun `given global ads disabled, then emit false`() = runTest {

    every { observeAdsConfig() } returns flowOf(adsConfig(globalEnabled = false))

    observeAdSlotVisibility(HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `given placement disabled, then emit false`() = runTest {

    every { observeAdsConfig() } returns flowOf(
      configWith(
        placement = HomeBottom,
        enabled = PLACEMENT_DISABLED
      )
    )

    observeAdSlotVisibility(HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `given placement missing in config, then emit false`() = runTest {

    every { observeAdsConfig() } returns flowOf(adsConfig(globalEnabled = true))

    observeAdSlotVisibility(HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `given consent not granted, then emit false`() = runTest {

    every { consentState.canRequestAds } returns flowOf(false)

    observeAdSlotVisibility(HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }
}
