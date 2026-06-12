package com.weather.vibe.feature.widget.analytics

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WidgetEventsTest {

  @Test
  fun `when widget added, then event name is widget added`() {

    expectThat(WidgetAddedEvent.name).isEqualTo("widget_added")
  }

  @Test
  fun `when widget removed, then event name is widget removed`() {

    expectThat(WidgetRemovedEvent.name).isEqualTo("widget_removed")
  }
}
