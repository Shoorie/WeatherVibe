package com.weather.vibe.notifications.work

import com.weather.vibe.domain.alerts.usecase.GatherWeatherAlerts
import com.weather.vibe.notifications.fake.fakeAlertsResources
import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.AlertNotifier
import com.weather.vibe.notifications.notification.alert.AlertNotificationFactory
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.HEAVY_RAIN
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.THUNDERSTORM
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CheckWeatherAlertsTest {

  private val gatherWeatherAlerts = mockk<GatherWeatherAlerts>()
  private val notifier = mockk<AlertNotifier>(relaxed = true)
  private val notificationFactory = AlertNotificationFactory(resources = fakeAlertsResources())
  private val checkWeatherAlerts = CheckWeatherAlerts(
    gatherWeatherAlerts = gatherWeatherAlerts,
    notificationFactory = notificationFactory,
    notifier = notifier
  )

  @Before
  fun setUp() {
    coEvery { gatherWeatherAlerts() } returns listOf(THUNDERSTORM, HEAVY_RAIN)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when invoked, then notification posted per alert`() = runTest {

    checkWeatherAlerts()

    verify(exactly = 2) { notifier.post(any<AlertNotification>()) }
  }

  @Test
  fun `when no alerts gathered, then nothing posted`() = runTest {

    coEvery { gatherWeatherAlerts() } returns emptyList()

    checkWeatherAlerts()

    verify(exactly = 0) { notifier.post(any()) }
  }
}
