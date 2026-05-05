package com.weather.vibe.scheduling

import com.weather.vibe.domain.alerts.usecase.GatherWeatherAlerts
import com.weather.vibe.domain.settings.usecase.AreWeatherAlertsEnabled
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.NotificationChannelKind
import com.weather.vibe.notifications.notification.alert.AlertNotificationFactory
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.HEAVY_RAIN
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.THUNDERSTORM
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CheckWeatherAlertsTest {

  private val areWeatherAlertsEnabled = mockk<AreWeatherAlertsEnabled>()
  private val gatherWeatherAlerts = mockk<GatherWeatherAlerts>()
  private val notifier = mockk<AlertNotifier>(relaxed = true)
  private val notificationFactory = mockk<AlertNotificationFactory>()
  private val checkWeatherAlerts = CheckWeatherAlerts(
    areWeatherAlertsEnabled = areWeatherAlertsEnabled,
    gatherWeatherAlerts = gatherWeatherAlerts,
    notificationFactory = notificationFactory,
    notifier = notifier
  )

  @Before
  fun setUp() {
    coEvery { areWeatherAlertsEnabled() } returns true
    coEvery { gatherWeatherAlerts() } returns listOf(THUNDERSTORM, HEAVY_RAIN)
    every { notificationFactory.create(any()) } returns MOCK_NOTIFICATION
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when alerts checked, then notification posted per alert`() = runTest {

    checkWeatherAlerts()

    verify(exactly = 2) { notifier.post(any<AlertNotification>()) }
  }

  @Test
  fun `given no alerts gathered, when alerts checked, then nothing posted`() = runTest {

    coEvery { gatherWeatherAlerts() } returns emptyList()

    checkWeatherAlerts()

    verify(exactly = 0) { notifier.post(any()) }
  }

  @Test
  fun `given weather alerts disabled, when checked, then nothing gathered or posted`() = runTest {

    coEvery { areWeatherAlertsEnabled() } returns false

    checkWeatherAlerts()

    verify(exactly = 0) { notifier.post(any()) }
  }

  private companion object {
    val MOCK_NOTIFICATION = AlertNotification(
      body = "body",
      id = 1,
      kind = NotificationChannelKind.WEATHER_ALERTS,
      title = "title"
    )
  }
}
