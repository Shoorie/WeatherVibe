package com.weather.vibe.feature.viberating.analytics

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RatingSavedEventTest {

  @Test
  fun `when rating saved, then event name is mood logged`() {

    expectThat(RatingSavedEvent.name).isEqualTo("mood_logged")
  }

  @Test
  fun `when rating saved, then no rating value is sent`() {

    expectThat(RatingSavedEvent.params).isEqualTo(emptyMap())
  }
}
