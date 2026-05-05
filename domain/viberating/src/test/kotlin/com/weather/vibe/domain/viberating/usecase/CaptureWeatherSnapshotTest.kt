package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.viberating.mapper.WeatherDataToVibeSnapshot
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.viberating.fixture.WeatherSnapshotFixtures.SUNNY_20C
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class CaptureWeatherSnapshotTest {

  private val getWeather = mockk<GetWeather>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val mapper = mockk<WeatherDataToVibeSnapshot>()
  private val captureWeatherSnapshot = CaptureWeatherSnapshot(
    getWeather = getWeather,
    observeCurrentLocation = observeCurrentLocation,
    weatherDataToVibeSnapshot = mapper
  )

  @Before
  fun setUp() {
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { getWeather(WARSAW.toCoordinates()) } returns flowOf(success(WEATHER))
    every { mapper.map(WEATHER) } returns SUNNY_20C
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when location and weather available, then snapshot returned`() = runTest {

    val snapshot = captureWeatherSnapshot()

    expectThat(snapshot).isEqualTo(SUNNY_20C)
  }

  @Test
  fun `given no current location, when captured, then unknown snapshot returned`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    val snapshot = captureWeatherSnapshot()

    expectThat(snapshot).isEqualTo(WeatherSnapshot.Unknown)
  }

  @Test
  fun `given weather fetch fails, when captured, then unknown snapshot returned`() = runTest {

    every { getWeather(WARSAW.toCoordinates()) } returns
      flowOf(failure(IllegalStateException("offline")))

    val snapshot = captureWeatherSnapshot()

    expectThat(snapshot).isEqualTo(WeatherSnapshot.Unknown)
  }
}
