package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.feature.widget.presentation.state.WidgetNoLocationUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetEmojis
import com.weather.vibe.feature.widget.ui.WidgetResources
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.SNAPSHOT
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class WidgetStateFactoryTest {

  private val resources = mockk<WidgetResources>()
  private val factory = WidgetStateFactory(resources = resources)

  @Before
  fun setUp() {
    every { resources.noLocationTitle() } returns NO_LOCATION_TITLE
    every { resources.noLocationBody() } returns NO_LOCATION_BODY
    every { resources.waitingTitle() } returns WAITING_TITLE
    every { resources.waitingBody(any()) } answers { "Fresh forecast for ${firstArg<String>()}" }
    every { resources.temperature(any()) } answers { "${firstArg<Int>()}°" }
    every { resources.weatherContentDescription(any(), any()) } returns CONTENT_DESCRIPTION
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when no location state created, then emoji is pinned location`() {

    val state = factory.createNoLocation()

    expectThat(state.emoji).isEqualTo(WidgetEmojis.PINNED_LOCATION)
  }

  @Test
  fun `when no location state created, then title matches resources`() {

    val state = factory.createNoLocation()

    expectThat(state.title).isEqualTo(NO_LOCATION_TITLE)
  }

  @Test
  fun `when waiting state created, then body formatted with location name`() {

    val state = factory.createWaiting(WARSAW)

    expectThat(state.body).isEqualTo("Fresh forecast for Warsaw")
  }

  @Test
  fun `when waiting state created, then emoji is hourglass`() {

    val state = factory.createWaiting(WARSAW)

    expectThat(state.emoji).isEqualTo(WidgetEmojis.HOURGLASS)
  }

  @Test
  fun `when ready state created, then location id preserved`() {

    val state = factory.createReady(SNAPSHOT)

    expectThat(state.locationId).isEqualTo(SNAPSHOT.location.id)
  }

  @Test
  fun `when ready state created, then condition emoji carried from snapshot`() {

    val state = factory.createReady(SNAPSHOT)

    expectThat(state.conditionEmoji).isEqualTo(SNAPSHOT.condition.emoji)
  }

  @Test
  fun `when ready state created, then temperature rounded and formatted via resources`() {

    val state = factory.createReady(SNAPSHOT)

    expectThat(state.temperature).isEqualTo("${SNAPSHOT.currentTemperature.toInt()}°")
  }

  @Test
  fun `when ready state created, then vibe text comes from suggestion`() {

    val state = factory.createReady(SNAPSHOT)

    expectThat(state).isA<WidgetReadyUiState>()
      .get { vibeText }.isEqualTo(SNAPSHOT.suggestion.briefText)
  }

  @Test
  fun `when no location state created, then returns dedicated type`() {

    val state = factory.createNoLocation()

    expectThat(state).isA<WidgetNoLocationUiState>()
  }

  @Test
  fun `when waiting state created, then returns dedicated type`() {

    val state = factory.createWaiting(WARSAW)

    expectThat(state).isA<WidgetWaitingUiState>()
  }

  private companion object {
    const val NO_LOCATION_TITLE = "Pick a city in WeatherVibe"
    const val NO_LOCATION_BODY = "Open the app"
    const val WAITING_TITLE = "Waking up the vibe"
    const val CONTENT_DESCRIPTION = "Weather for Warsaw"
  }
}
