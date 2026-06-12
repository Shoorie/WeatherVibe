package com.weather.vibe.notifications.analytics

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class NotificationShownEventTest {

  @Test
  fun `when notification shown, then kind param carries channel kind`() {

    val event = NotificationShownEvent(kind = "MOOD_REMINDER")

    expectThat(event.name).isEqualTo("notification_shown")
    expectThat(event.params).isEqualTo(mapOf("kind" to "MOOD_REMINDER"))
  }
}
