package com.weather.vibe.feature.onboarding.presentation.welcome

import com.weather.vibe.core.permissions.notification.NotificationPermissionSupport
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.BRIEF
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.READY
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.TALK
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlides
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeResources
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class WelcomeStateFactoryTest {

  private val notificationPermissionSupport = mockk<NotificationPermissionSupport>()
  private val resources = mockk<WelcomeResources>(relaxed = true)
  private val factory = WelcomeStateFactory(
    notificationPermission = notificationPermissionSupport,
    resources = resources
  )

  @Before
  fun setUp() {
    every { resources.nextLabel() } returns NEXT_LABEL
    every { resources.enableNotificationsAndFinishLabel() } returns ENABLE_LABEL
    every { resources.finishLabel() } returns FINISH_LABEL
    every { resources.skipNotificationsLabel() } returns SKIP_NOTIFICATIONS_LABEL
    every { notificationPermissionSupport.isSupported() } returns true
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when first slide created, then slide is talk`() {

    val state = factory.create(slideIndex = 0)

    expectThat(state.slide).isEqualTo(TALK)
  }

  @Test
  fun `when second slide created, then slide is brief`() {

    val state = factory.create(slideIndex = 1)

    expectThat(state.slide).isEqualTo(BRIEF)
  }

  @Test
  fun `when last slide created, then slide is ready`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.slide).isEqualTo(READY)
  }

  @Test
  fun `when last slide created, then marked as final slide`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.isFinalSlide).isTrue()
  }

  @Test
  fun `when last slide created, then skip is hidden`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.skipVisible).isFalse()
  }

  @Test
  fun `given notification permission supported, when last slide created, then enable notifications label exposed`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.primaryActionLabel).isEqualTo(ENABLE_LABEL)
  }

  @Test
  fun `given notification permission unsupported, when last slide created, then finish label exposed`() {

    every { notificationPermissionSupport.isSupported() } returns false

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.primaryActionLabel).isEqualTo(FINISH_LABEL)
  }

  @Test
  fun `when intermediate slide created, then next label exposed`() {

    val state = factory.create(slideIndex = 2)

    expectThat(state.primaryActionLabel).isEqualTo(NEXT_LABEL)
  }

  @Test
  fun `when intermediate slide created, then skip notifications label hidden`() {

    val state = factory.create(slideIndex = 2)

    expectThat(state.skipNotificationsLabel).isEqualTo(null)
  }

  @Test
  fun `given notification permission supported, when last slide created, then skip notifications label exposed`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.skipNotificationsLabel).isEqualTo(SKIP_NOTIFICATIONS_LABEL)
  }

  @Test
  fun `given notification permission unsupported, when last slide created, then skip notifications label hidden`() {

    every { notificationPermissionSupport.isSupported() } returns false

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.skipNotificationsLabel).isEqualTo(null)
  }

  @Test
  fun `when slide created, then total slides count matches enum size`() {

    val state = factory.create(slideIndex = 0)

    expectThat(state.totalSlides).isEqualTo(WelcomeSlides.ALL.size)
  }

  @Test
  fun `when last slide created, then three notification preview cards exposed`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.notificationCards).hasSize(3)
  }

  private companion object {
    const val NEXT_LABEL = "Next"
    const val ENABLE_LABEL = "Enable notifications and start"
    const val FINISH_LABEL = "Let's go"
    const val SKIP_NOTIFICATIONS_LABEL = "Maybe later"
  }
}
