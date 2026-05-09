package com.weather.vibe.core.ads.domain.usecase

import app.cash.turbine.test
import com.weather.vibe.core.ads.consent.ConsentManager
import com.weather.vibe.core.ads.domain.AdPlacement
import com.weather.vibe.core.ads.domain.config.AdPlacementConfig
import com.weather.vibe.core.ads.domain.config.AdsConfig
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
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when global enabled and placement enabled and consent granted, then emit true`() =
    runTest {
      stubConfig(globalEnabled = true, placementEnabled = true)

      observeAdSlotVisibility(AdPlacement.HomeBottom).test {
        expectThat(awaitItem()).isEqualTo(true)
        awaitComplete()
      }
    }

  @Test
  fun `when global disabled, then emit false`() = runTest {
    stubConfig(globalEnabled = false, placementEnabled = true)

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `when placement disabled, then emit false`() = runTest {
    stubConfig(globalEnabled = true, placementEnabled = false)

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `when placement missing in config, then emit false`() = runTest {
    every { observeAdsConfig() } returns flowOf(AdsConfig(globalEnabled = true))

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  @Test
  fun `when consent not granted, then emit false`() = runTest {
    stubConfig(globalEnabled = true, placementEnabled = true)
    every { consentManager.canRequestAds } returns flowOf(false)

    observeAdSlotVisibility(AdPlacement.HomeBottom).test {
      expectThat(awaitItem()).isEqualTo(false)
      awaitComplete()
    }
  }

  private fun stubConfig(globalEnabled: Boolean, placementEnabled: Boolean) {
    every { observeAdsConfig() } returns flowOf(
      AdsConfig(
        globalEnabled = globalEnabled,
        placements = mapOf(
          AdPlacement.HomeBottom.key to AdPlacementConfig(enabled = placementEnabled)
        )
      )
    )
  }
}
