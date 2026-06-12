package com.weather.vibe.feature.profile.analytics

import com.weather.vibe.core.analytics.AnalyticsLogger
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ProfileAnalyticsTest {

  private val logger = mockk<AnalyticsLogger>(relaxed = true)
  private val analytics = ProfileAnalytics(logger = logger)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when username saved, then username set event is logged`() {

    analytics.onUsernameSaved()

    verify { logger.log(UsernameSetEvent) }
  }

  @Test
  fun `when username saved, then has username property is set to true`() {

    analytics.onUsernameSaved()

    verify { logger.setUserProperty(HasUsernameProperty(hasUsername = true)) }
  }

  @Test
  fun `when username present, then has username property value is true`() {

    val property = HasUsernameProperty(hasUsername = true)

    expectThat(property.name).isEqualTo("has_username")
    expectThat(property.value).isEqualTo("true")
  }
}
