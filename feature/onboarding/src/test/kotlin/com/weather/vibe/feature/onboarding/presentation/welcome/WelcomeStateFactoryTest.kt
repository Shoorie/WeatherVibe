package com.weather.vibe.feature.onboarding.presentation.welcome

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
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class WelcomeStateFactoryTest {

  private val resources = mockk<WelcomeResources>(relaxed = true)
  private val factory = WelcomeStateFactory(resources = resources)

  @Before
  fun setUp() {
    every { resources.ctaFor(isFinal = false) } returns NEXT_LABEL
    every { resources.ctaFor(isFinal = true) } returns FINISH_LABEL
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
  fun `when last slide created, then is final slide`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.isFinalSlide).isTrue()
  }

  @Test
  fun `when last slide created, then skip is hidden`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.skipVisible).isFalse()
  }

  @Test
  fun `when last slide created, then cta uses finish label`() {

    val state = factory.create(slideIndex = WelcomeSlides.LAST_INDEX)

    expectThat(state.ctaLabel).isEqualTo(FINISH_LABEL)
  }

  @Test
  fun `when intermediate slide created, then cta uses next label`() {

    val state = factory.create(slideIndex = 2)

    expectThat(state.ctaLabel).isEqualTo(NEXT_LABEL)
  }

  @Test
  fun `when slide created, then total slides count matches enum size`() {

    val state = factory.create(slideIndex = 0)

    expectThat(state.totalSlides).isEqualTo(WelcomeSlides.ALL.size)
  }

  private companion object {
    const val NEXT_LABEL = "Next"
    const val FINISH_LABEL = "Finish"
  }
}
