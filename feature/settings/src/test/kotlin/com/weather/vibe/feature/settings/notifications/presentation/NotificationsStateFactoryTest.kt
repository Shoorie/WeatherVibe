package com.weather.vibe.feature.settings.notifications.presentation

import com.weather.vibe.feature.settings.notifications.presentation.fake.NOTIFICATIONS_DEFAULT_ERROR
import com.weather.vibe.feature.settings.notifications.presentation.fake.fakeNotificationsResources
import com.weather.vibe.feature.settings.notifications.presentation.state.NotificationsUiState
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class NotificationsStateFactoryTest {

  private val resources = fakeNotificationsResources()
  private val factory = NotificationsStateFactory(resources = resources)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when initial state built, then alerts disabled`() {

    val result = factory.initial()

    expectThat(result.alertsEnabled).isFalse()
  }

  @Test
  fun `when initial state built, then morning brief disabled`() {

    val result = factory.initial()

    expectThat(result.morningBriefEnabled).isFalse()
  }

  @Test
  fun `given alerts enabled settings, when state created, then alerts enabled`() {

    val result = factory.create(settings = userSettings(alertsEnabled = true))

    expectThat(result.alertsEnabled).isTrue()
  }

  @Test
  fun `given morning brief enabled settings, when state created, then morning brief enabled`() {

    val result = factory.create(settings = userSettings(morningBriefEnabled = true))

    expectThat(result.morningBriefEnabled).isTrue()
  }

  @Test
  fun `when error created, then message matches default error resource`() {

    val result = factory.createError()

    expectThat(result).isA<NotificationsUiState.Error>()
      .get { message }.isEqualTo(NOTIFICATIONS_DEFAULT_ERROR)
  }
}
