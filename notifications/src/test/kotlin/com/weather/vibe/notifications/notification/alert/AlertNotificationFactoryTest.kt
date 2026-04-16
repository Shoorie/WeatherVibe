package com.weather.vibe.notifications.notification.alert

import com.weather.vibe.domain.alerts.model.WeatherAlert.HeavyRainImminent
import com.weather.vibe.domain.alerts.model.WeatherAlert.SharpTemperatureDrop
import com.weather.vibe.domain.alerts.model.WeatherAlert.ThunderstormImminent
import com.weather.vibe.notifications.fake.fakeAlertsResources
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.HEAVY_RAIN_TITLE
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.TEMPERATURE_DROP_TITLE
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.THUNDERSTORM_TITLE
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.time.LocalDateTime

class AlertNotificationFactoryTest {

  private val factory = AlertNotificationFactory(resources = fakeAlertsResources())

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when thunderstorm alert mapped, then title from resources`() {

    val notification = factory.create(ThunderstormImminent(expectedAt = AT))

    expectThat(notification.title).isEqualTo(THUNDERSTORM_TITLE)
  }

  @Test
  fun `when heavy rain alert mapped, then title from resources`() {

    val notification = factory.create(
      HeavyRainImminent(expectedAt = AT, millimetres = 6.3)
    )

    expectThat(notification.title).isEqualTo(HEAVY_RAIN_TITLE)
  }

  @Test
  fun `when heavy rain alert mapped, then body carries rounded millimetres`() {

    val notification = factory.create(
      HeavyRainImminent(expectedAt = AT, millimetres = 6.6)
    )

    expectThat(notification.body).contains("7 mm")
  }

  @Test
  fun `when temperature drop alert mapped, then title from resources`() {

    val notification = factory.create(
      SharpTemperatureDrop(expectedAt = AT, degreesCelsius = 9.0)
    )

    expectThat(notification.title).isEqualTo(TEMPERATURE_DROP_TITLE)
  }

  @Test
  fun `when temperature drop alert mapped, then body carries rounded degrees`() {

    val notification = factory.create(
      SharpTemperatureDrop(expectedAt = AT, degreesCelsius = 9.4)
    )

    expectThat(notification.body).contains("9°")
  }

  @Test
  fun `when different alert types mapped, then notification ids differ`() {

    val storm = factory.create(ThunderstormImminent(expectedAt = AT))
    val rain = factory.create(HeavyRainImminent(expectedAt = AT, millimetres = 6.0))

    expectThat(storm.id).isNotEqualTo(rain.id)
  }

  private companion object {
    val AT: LocalDateTime = LocalDateTime.of(2026, 4, 8, 15, 30)
  }
}
