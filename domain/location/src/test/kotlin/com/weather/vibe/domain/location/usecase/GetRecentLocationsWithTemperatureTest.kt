package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.usecase.GetCurrentTemperature
import com.weather.vibe.testing.location.fixture.LocationFixtures.GDANSK
import com.weather.vibe.testing.location.fixture.LocationFixtures.KRAKOW
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import strikt.assertions.isSuccess
import java.io.IOException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class GetRecentLocationsWithTemperatureTest {

  private val getRecentLocations = mockk<GetRecentLocations>()
  private val getCurrentTemperature = mockk<GetCurrentTemperature>()
  private val getRecentLocationsWithTemperature = GetRecentLocationsWithTemperature(
    getCurrentTemperature = getCurrentTemperature,
    getRecentLocations = getRecentLocations
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given no recent locations, then empty list returned`() = runTest {

    every { getRecentLocations() } returns flowOf(success(emptyList()))

    val result = getRecentLocationsWithTemperature().first()

    expectThat(result).isSuccess().isEmpty()
  }

  @Test
  fun `when recent locations loaded, then each returned with its temperature`() = runTest {

    every { getRecentLocations() } returns flowOf(success(listOf(WARSAW, KRAKOW, GDANSK)))
    mockTemperature(WARSAW, success(10.0))
    mockTemperature(KRAKOW, success(12.0))
    mockTemperature(GDANSK, success(8.0))

    val result = getRecentLocationsWithTemperature().first()

    expectThat(result).isSuccess().containsExactly(
      LocationWithTemperature(WARSAW, 10.0),
      LocationWithTemperature(KRAKOW, 12.0),
      LocationWithTemperature(GDANSK, 8.0)
    )
  }

  @Test
  fun `given one temperature fetch fails, then affected location has no temperature`() = runTest {

    every { getRecentLocations() } returns flowOf(
      success(listOf(WARSAW, KRAKOW, GDANSK))
    )
    mockTemperature(WARSAW, success(10.0))
    mockTemperature(KRAKOW, failure(IOException("network")))
    mockTemperature(GDANSK, success(8.0))

    val result = getRecentLocationsWithTemperature().first()

    expectThat(result).isSuccess().containsExactly(
      LocationWithTemperature(WARSAW, 10.0),
      LocationWithTemperature(KRAKOW, null),
      LocationWithTemperature(GDANSK, 8.0)
    )
  }

  @Test
  fun `given recent locations fetch fails, then failure returned`() = runTest {

    val error = IOException("db unavailable")
    every { getRecentLocations() } returns flowOf(failure(error))

    val result = getRecentLocationsWithTemperature().first()

    expectThat(result).isFailure().isEqualTo(error)
  }

  private fun mockTemperature(location: Location, result: Result<Double>) {
    every { getCurrentTemperature(location.toCoordinates()) } returns
      flowOf(result)
  }
}
