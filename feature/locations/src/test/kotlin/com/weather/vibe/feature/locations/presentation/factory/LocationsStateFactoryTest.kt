package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.feature.locations.presentation.fake.DEFAULT_ERROR
import com.weather.vibe.feature.locations.presentation.fake.fakeLocationsResources
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class LocationsStateFactoryTest {

  private val factory = LocationsStateFactory(
    cardFactory = LocationCardFactory(weatherFactory = LocationWeatherFactory()),
    resources = fakeLocationsResources()
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when map cards built from sources, then produces one card per source in order`() {

    val sources = listOf(FavoriteFixtures.WARSAW_WITH_WEATHER, FavoriteFixtures.KRAKOW_WITH_WEATHER)

    val result = factory.mapCards(sources = sources)

    expectThat(result).hasSize(2)
  }

  @Test
  fun `given empty sources, when map cards called, then returns empty list`() {

    val result = factory.mapCards(sources = emptyList())

    expectThat(result).isEmpty()
  }

  @Test
  fun `given throwable with message, when error built, then state uses throwable message`() {

    val result = factory.error(throwable = IllegalStateException("boom"))

    expectThat(result.message).isEqualTo("boom")
  }

  @Test
  fun `given throwable without message, when error built, then state uses default error`() {

    val result = factory.error(throwable = RuntimeException())

    expectThat(result.message).isEqualTo(DEFAULT_ERROR)
  }
}
