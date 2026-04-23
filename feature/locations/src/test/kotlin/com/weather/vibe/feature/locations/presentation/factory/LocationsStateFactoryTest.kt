package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.locations.presentation.fake.DEFAULT_ERROR
import com.weather.vibe.feature.locations.presentation.fake.fakeLocationsResources
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures
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
  fun `map cards produces one card per source in order`() {

    val sources = listOf(LocationFavoriteFixtures.WARSAW_WITH_WEATHER, LocationFavoriteFixtures.KRAKOW_WITH_WEATHER)

    val result = factory.mapCards(sources = sources, temperatureUnit = CELSIUS)

    expectThat(result).hasSize(2)
  }

  @Test
  fun `empty sources produce empty card list`() {

    val result = factory.mapCards(sources = emptyList(), temperatureUnit = CELSIUS)

    expectThat(result).isEmpty()
  }

  @Test
  fun `throwable with message surfaces as error state message`() {

    val result = factory.error(throwable = IllegalStateException("boom"))

    expectThat(result.message).isEqualTo("boom")
  }

  @Test
  fun `throwable without message falls back to default error`() {

    val result = factory.error(throwable = RuntimeException())

    expectThat(result.message).isEqualTo(DEFAULT_ERROR)
  }
}
