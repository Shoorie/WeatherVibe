package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.WidgetPromoOutcome.Reveal
import com.weather.vibe.domain.settings.model.WidgetPromoOutcome.Skip
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.coVerify
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

class ResolveWidgetPromoTest {

  private val markWidgetPromoEligible = mockk<MarkWidgetPromoEligible>(relaxed = true)
  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val resolveWidgetPromo = ResolveWidgetPromo(
    markWidgetPromoEligible = markWidgetPromoEligible,
    observeUserSettings = observeUserSettings
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given welcome onboarding not seen, then skip`() = runTest {

    every { observeUserSettings() } returns
      flowOf(success(userSettings(welcomeOnboardingSeen = false)))

    expectThat(resolveWidgetPromo()).isEqualTo(Skip)
  }

  @Test
  fun `given widget promo already seen, then skip`() = runTest {

    every { observeUserSettings() } returns flowOf(
      success(
        userSettings(
          welcomeOnboardingSeen = true,
          widgetPromoEligible = true,
          widgetPromoSeen = true
        )
      )
    )

    expectThat(resolveWidgetPromo()).isEqualTo(Skip)
  }

  @Test
  fun `given first home arrival after onboarding, then mark eligible and skip`() = runTest {

    every { observeUserSettings() } returns flowOf(
      success(
        userSettings(
          welcomeOnboardingSeen = true,
          widgetPromoEligible = false,
          widgetPromoSeen = false
        )
      )
    )

    val outcome = resolveWidgetPromo()

    expectThat(outcome).isEqualTo(Skip)
    coVerify { markWidgetPromoEligible() }
  }

  @Test
  fun `given second home arrival after onboarding, then reveal sheet`() = runTest {

    every { observeUserSettings() } returns flowOf(
      success(
        userSettings(
          welcomeOnboardingSeen = true,
          widgetPromoEligible = true,
          widgetPromoSeen = false
        )
      )
    )

    expectThat(resolveWidgetPromo()).isEqualTo(Reveal)
  }

  @Test
  fun `given settings read fails, then skip`() = runTest {

    every { observeUserSettings() } returns flowOf(failure(RuntimeException("boom")))

    expectThat(resolveWidgetPromo()).isEqualTo(Skip)
  }
}
