package com.weather.vibe.feature.search.presentation

import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.location.fixture.LocationFixtures.location
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class SearchStateFactoryTest {

  private val temperature = mockk<TemperatureFormatter>()
  private val factory = SearchStateFactory(
    subtitle = LocationSubtitleFormatter(),
    temperature = temperature
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given no entries, when items created, then empty list returned`() {

    val result = factory.createItems(entries = emptyList(), unit = CELSIUS)

    expectThat(result).isEmpty()
  }

  @Test
  fun `given entry with temperature, when items created, then formatted temperature returned`() {

    val entries = listOf(LocationWithTemperature(WARSAW, currentTemperature = 15.4))
    every { temperature.format(celsius = 15.4, unit = CELSIUS) } returns "15°"

    val result = factory.createItems(entries = entries, unit = CELSIUS)

    expectThat(result.single().temperature).isEqualTo("15°")
  }

  @Test
  fun `given entry without temperature, when items created, then temperature is null`() {

    val entries = listOf(LocationWithTemperature(WARSAW, currentTemperature = null))

    val result = factory.createItems(entries = entries, unit = CELSIUS)

    expectThat(result.single().temperature).isNull()
  }

  @Test
  fun `given admin1 null, when items created, then subtitle contains only country`() {

    val entries = listOf(
      LocationWithTemperature(
        location = location(admin1 = null, country = "Poland"),
        currentTemperature = null
      )
    )

    val result = factory.createItems(entries = entries, unit = CELSIUS)

    expectThat(result.single().subtitle).isEqualTo("Poland")
  }

  @Test
  fun `given admin1 and country, when items created, then subtitle contains both separated by comma`() {

    val entries = listOf(
      LocationWithTemperature(
        location = location(admin1 = "Mazowieckie", country = "Poland"),
        currentTemperature = null
      )
    )

    val result = factory.createItems(entries = entries, unit = CELSIUS)

    expectThat(result.single().subtitle).isEqualTo("Mazowieckie, Poland")
  }

  @Test
  fun `given empty country, when items created, then subtitle contains only admin1`() {

    val entries = listOf(
      LocationWithTemperature(
        location = location(admin1 = "Mazowieckie", country = ""),
        currentTemperature = null
      )
    )

    val result = factory.createItems(entries = entries, unit = CELSIUS)

    expectThat(result.single().subtitle).isEqualTo("Mazowieckie")
  }
}
