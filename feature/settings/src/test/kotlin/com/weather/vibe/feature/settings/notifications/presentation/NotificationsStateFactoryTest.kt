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
import strikt.assertions.isTrue

internal class NotificationsStateFactoryTest {

  private val resources = fakeNotificationsResources()
  private val factory = NotificationsStateFactory(resources = resources)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given weather alerts enabled settings, when state created, then weather alerts enabled`() {

    val result = factory.create(settings = userSettings(weatherAlertsEnabled = true))

    expectThat(result.weatherAlertsEnabled).isTrue()
  }

  @Test
  fun `given pollen alerts enabled settings, when state created, then pollen alerts enabled`() {

    val result = factory.create(settings = userSettings(pollenAlertsEnabled = true))

    expectThat(result.pollenAlertsEnabled).isTrue()
  }

  @Test
  fun `given morning brief enabled settings, when state created, then morning brief enabled`() {

    val result = factory.create(settings = userSettings(morningBriefEnabled = true))

    expectThat(result.morningBriefEnabled).isTrue()
  }

  @Test
  fun `given mood reminder enabled settings, when state created, then mood reminder enabled`() {

    val result = factory.create(settings = userSettings(moodReminderEnabled = true))

    expectThat(result.moodReminderEnabled).isTrue()
  }

  @Test
  fun `when error created, then message matches default error resource`() {

    val result = factory.createError()

    expectThat(result).isA<NotificationsUiState.Error>()
      .get { message }.isEqualTo(NOTIFICATIONS_DEFAULT_ERROR)
  }
}
