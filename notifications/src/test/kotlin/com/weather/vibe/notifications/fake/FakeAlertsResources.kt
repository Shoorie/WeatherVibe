package com.weather.vibe.notifications.fake

import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.CHANNEL_NAME
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.ERROR_BODY_PREFIX
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.HEAVY_RAIN_TITLE
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.TEMPERATURE_DROP_TITLE
import com.weather.vibe.notifications.fixture.AlertsResourcesFixtures.THUNDERSTORM_TITLE
import com.weather.vibe.notifications.ui.AlertsResources
import io.mockk.every
import io.mockk.mockk
import java.time.format.DateTimeFormatter

internal fun fakeAlertsResources(): AlertsResources {
  val formatter = DateTimeFormatter.ofPattern("HH:mm")
  return mockk<AlertsResources>().apply {
    every { channelName() } returns CHANNEL_NAME
    every { channelDescription() } returns "$ERROR_BODY_PREFIX channel description"
    every { thunderstormTitle() } returns THUNDERSTORM_TITLE
    every { thunderstormBody(any()) } answers {
      "Storm at ${firstArg<java.time.LocalDateTime>().format(formatter)}"
    }
    every { heavyRainTitle() } returns HEAVY_RAIN_TITLE
    every { heavyRainBody(any(), any()) } answers {
      "Rain ${secondArg<Int>()} mm at ${firstArg<java.time.LocalDateTime>().format(formatter)}"
    }
    every { temperatureDropTitle() } returns TEMPERATURE_DROP_TITLE
    every { temperatureDropBody(any(), any()) } answers {
      "Drop ${secondArg<Int>()}° by ${firstArg<java.time.LocalDateTime>().format(formatter)}"
    }
  }
}
