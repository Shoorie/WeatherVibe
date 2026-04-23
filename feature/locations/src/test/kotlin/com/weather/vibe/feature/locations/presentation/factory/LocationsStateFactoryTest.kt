package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.locations.presentation.fake.DEFAULT_ERROR
import com.weather.vibe.feature.locations.presentation.fake.fakeLocationsResources
import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.KRAKOW_WITH_WEATHER
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_WITH_WEATHER
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class LocationsStateFactoryTest {

  private val temperatureFormatter = fakeTemperatureFormatter()
  private val factory = LocationsStateFactory(
    cardFactory = LocationCardFactory(
      temperatureFormatter = temperatureFormatter,
      weatherFactory = LocationWeatherFactory()
    ),
    resources = fakeLocationsResources()
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given two sources, when cards mapped, then result has two cards`() {

    val sources = listOf(WARSAW_WITH_WEATHER, KRAKOW_WITH_WEATHER)

    val result = factory.mapCards(sources = sources, temperatureUnit = CELSIUS)

    expectThat(result).hasSize(2)
  }

  @Test
  fun `given empty sources, when cards mapped, then result is empty`() {

    val result = factory.mapCards(sources = emptyList(), temperatureUnit = CELSIUS)

    expectThat(result).isEmpty()
  }

  @Test
  fun `given throwable with message, when error state built, then message is preserved`() {

    val result = factory.error(throwable = IllegalStateException("boom"))

    expectThat(result.message).isEqualTo("boom")
  }

  @Test
  fun `given throwable without message, when error state built, then message falls back to default`() {

    val result = factory.error(throwable = RuntimeException())

    expectThat(result.message).isEqualTo(DEFAULT_ERROR)
  }
}
