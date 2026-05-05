package com.weather.vibe.scheduling

import com.weather.vibe.domain.alerts.usecase.GatherPollenAlerts
import com.weather.vibe.domain.settings.usecase.ArePollenAlertsEnabled
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.NotificationChannelKind
import com.weather.vibe.notifications.notification.alert.AlertNotificationFactory
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.HIGH_POLLEN
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CheckPollenAlertsTest {

  private val arePollenAlertsEnabled = mockk<ArePollenAlertsEnabled>()
  private val gatherPollenAlerts = mockk<GatherPollenAlerts>()
  private val notifier = mockk<AlertNotifier>(relaxed = true)
  private val notificationFactory = mockk<AlertNotificationFactory>()
  private val checkPollenAlerts = CheckPollenAlerts(
    arePollenAlertsEnabled = arePollenAlertsEnabled,
    gatherPollenAlerts = gatherPollenAlerts,
    notificationFactory = notificationFactory,
    notifier = notifier
  )

  @Before
  fun setUp() {
    coEvery { arePollenAlertsEnabled() } returns true
    coEvery { gatherPollenAlerts() } returns listOf(HIGH_POLLEN)
    every { notificationFactory.create(any()) } returns MOCK_NOTIFICATION
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when pollen alert gathered, then notification posted`() = runTest {

    checkPollenAlerts()

    verify(exactly = 1) { notifier.post(any<AlertNotification>()) }
  }

  @Test
  fun `given pollen below threshold, when checked, then nothing posted`() = runTest {

    coEvery { gatherPollenAlerts() } returns emptyList()

    checkPollenAlerts()

    verify(exactly = 0) { notifier.post(any()) }
  }

  @Test
  fun `given pollen alerts disabled, when checked, then nothing gathered or posted`() = runTest {

    coEvery { arePollenAlertsEnabled() } returns false

    checkPollenAlerts()

    verify(exactly = 0) { notifier.post(any()) }
  }

  private companion object {
    val MOCK_NOTIFICATION = AlertNotification(
      body = "body",
      id = 1005,
      kind = NotificationChannelKind.POLLEN_ALERTS,
      title = "title"
    )
  }
}
