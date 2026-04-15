package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import com.weather.vibe.feature.widget.ui.WidgetEmojis
import com.weather.vibe.feature.widget.ui.WidgetResources
import com.weather.vibe.feature.widget.presentation.WidgetTimestampFormatter
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
  private val formatTimestamp = mockk<WidgetTimestampFormatter>()
  private val factory = WidgetStateFactory(
    resources = resources,
    formatTimestamp = formatTimestamp
  )

  @Before
  fun setUp() {
    every { resources.noLocationTitle() } returns NO_LOCATION_TITLE
    every { resources.noLocationBody() } returns NO_LOCATION_BODY
    every { resources.waitingTitle() } returns WAITING_TITLE
    every { resources.waitingBody(any()) } answers { "Fresh forecast for ${firstArg<String>()}" }
    every { resources.errorTitle() } returns ERROR_TITLE
    every { resources.errorBody() } returns ERROR_BODY
    every { resources.temperature(any()) } answers { "${firstArg<Int>()}°" }
    every { resources.weatherContentDescription(any(), any()) } returns CONTENT_DESCRIPTION
    every { formatTimestamp(any()) } returns FETCHED_AT_LABEL
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when no location state created, then emoji is pinned location`() {

    val state = factory.createNoLocation()

    expectThat(state.message.emoji).isEqualTo(WidgetEmojis.PINNED_LOCATION)
  }

  @Test
  fun `when no location state created, then title matches resources`() {

    val state = factory.createNoLocation()

    expectThat(state.message.title).isEqualTo(NO_LOCATION_TITLE)
  }

  @Test
  fun `when waiting state created, then body formatted with location name`() {

    val state = factory.createWaitingFor(WARSAW)

    expectThat(state.message.body).isEqualTo("Fresh forecast for Warsaw")
  }

  @Test
  fun `when waiting state created, then emoji is hourglass`() {

    val state = factory.createWaitingFor(WARSAW)

    expectThat(state.message.emoji).isEqualTo(WidgetEmojis.HOURGLASS)
  }

  @Test
  fun `when weather state created, then location id preserved`() {

    val state = factory.createWeather(SNAPSHOT)

    expectThat(state.locationId).isEqualTo(SNAPSHOT.location.id)
  }

  @Test
  fun `when weather state created, then condition emoji carried from snapshot`() {

    val state = factory.createWeather(SNAPSHOT)

    expectThat(state.conditionEmoji).isEqualTo(SNAPSHOT.condition.emoji)
  }

  @Test
  fun `when weather state created, then temperature rounded and formatted via resources`() {

    val state = factory.createWeather(SNAPSHOT)

    expectThat(state.temperature).isEqualTo("${SNAPSHOT.currentTemperature.toInt()}°")
  }

  @Test
  fun `when weather state created, then mood comes from snapshot`() {

    val state = factory.createWeather(SNAPSHOT)

    expectThat(state).isA<WidgetUiState.Weather>()
      .get { mood }.isEqualTo(SNAPSHOT.mood)
  }

  @Test
  fun `when weather state created, then fetched at label formatted from snapshot timestamp`() {

    val state = factory.createWeather(SNAPSHOT)

    expectThat(state.fetchedAtLabel).isEqualTo(FETCHED_AT_LABEL)
  }

  @Test
  fun `when weather state created, then condition label carried from snapshot`() {

    val state = factory.createWeather(SNAPSHOT)

    expectThat(state.conditionLabel).isEqualTo(SNAPSHOT.condition.label)
  }

  @Test
  fun `when no location state created, then returns dedicated type`() {

    val state = factory.createNoLocation()

    expectThat(state).isA<WidgetUiState.NoLocation>()
  }

  @Test
  fun `when waiting state created, then returns dedicated type`() {

    val state = factory.createWaitingFor(WARSAW)

    expectThat(state).isA<WidgetUiState.Waiting>()
  }

  @Test
  fun `when error state created, then emoji is storm`() {

    val state = factory.createError()

    expectThat(state.message.emoji).isEqualTo(WidgetEmojis.STORM)
  }

  @Test
  fun `when error state created, then title and body come from resources`() {

    val state = factory.createError()

    expectThat(state).isA<WidgetUiState.Error>()
      .and {
        get { message.title }.isEqualTo(ERROR_TITLE)
        get { message.body }.isEqualTo(ERROR_BODY)
      }
  }

  private companion object {
    const val NO_LOCATION_TITLE = "Pick a city in WeatherVibe"
    const val NO_LOCATION_BODY = "Open the app"
    const val WAITING_TITLE = "Waking up the vibe"
    const val ERROR_TITLE = "Couldn't load the vibe"
    const val ERROR_BODY = "Tap to open WeatherVibe"
    const val CONTENT_DESCRIPTION = "Weather for Warsaw"
    const val FETCHED_AT_LABEL = "12:30"
  }
}
