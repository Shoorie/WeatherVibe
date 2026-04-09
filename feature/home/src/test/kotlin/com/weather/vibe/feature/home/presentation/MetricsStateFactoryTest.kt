package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.weather.model.WindDirection
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.METRICS
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherMetrics
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class MetricsStateFactoryTest {

  private val temperatureFormatter = mockk<TemperatureFormatter>()
  private val resources: HomeResources = fakeHomeResources()

  private val factory: MetricsStateFactory =
    MetricsStateFactory(
      resources = resources,
      temperature = temperatureFormatter
    )

  @Before
  fun setUp() {
    every { temperatureFormatter.format(celsius = any(), unit = any()) } answers {
      "${firstArg<Double>().toInt()}°"
    }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when details created, then wind section has four items`() {

    val result = factory.create(METRICS)

    expectThat(result.wind).hasSize(4)
  }

  @Test
  fun `when details created, then map wind speed`() {

    val result = factory.create(weatherMetrics(windSpeed = 15.0))

    expectThat(result.wind[0].value).isEqualTo("15 km/h")
  }

  @Test
  fun `when details created, then map wind speed label`() {

    val result = factory.create(METRICS)

    expectThat(result.wind[0].label).isEqualTo("Wind Speed")
  }

  @Test
  fun `given north wind, when details created, then show N direction`() {

    val result = factory.create(weatherMetrics(windDirection = WindDirection.N))

    expectThat(result.wind[1].value).isEqualTo("N")
  }

  @Test
  fun `given south wind, when details created, then show S direction`() {

    val result = factory.create(weatherMetrics(windDirection = WindDirection.S))

    expectThat(result.wind[1].value).isEqualTo("S")
  }

  @Test
  fun `when details created, then map wind gusts`() {

    val result = factory.create(weatherMetrics(windGusts = 25.0))

    expectThat(result.wind[2].value).isEqualTo("25 km/h")
  }

  @Test
  fun `when details created, then map max wind speed label`() {

    val result = factory.create(METRICS)

    expectThat(result.wind[3].label).isEqualTo("Max Wind Speed")
  }

  @Test
  fun `when details created, then atmosphere section has four items`() {

    val result = factory.create(METRICS)

    expectThat(result.atmosphere).hasSize(4)
  }

  @Test
  fun `when details created, then map humidity as percentage`() {

    val result = factory.create(weatherMetrics(humidity = 65))

    expectThat(result.atmosphere[0].value).isEqualTo("65%")
  }

  @Test
  fun `when details created, then map humidity label`() {

    val result = factory.create(METRICS)

    expectThat(result.atmosphere[0].label).isEqualTo("Humidity")
  }

  @Test
  fun `when details created, then map pressure with hPa unit`() {

    val result = factory.create(weatherMetrics(surfacePressure = 1013.25))

    expectThat(result.atmosphere[1].value).isEqualTo("1013 hPa")
  }

  @Test
  fun `when details created, then map dew point as temperature`() {

    val result = factory.create(weatherMetrics(dewPoint = 12.0))

    expectThat(result.atmosphere[2].value).isEqualTo("12°")
  }

  @Test
  fun `when details created, then map cloud cover as percentage`() {

    val result = factory.create(weatherMetrics(cloudCover = 45))

    expectThat(result.atmosphere[3].value).isEqualTo("45%")
  }

  @Test
  fun `when details created, then conditions section has four items`() {

    val result = factory.create(METRICS)

    expectThat(result.conditions).hasSize(4)
  }

  @Test
  fun `when details created, then map precipitation label`() {

    val result = factory.create(METRICS)

    expectThat(result.conditions[0].label).isEqualTo("Precipitation")
  }

  @Test
  fun `when details created, then map precipitation as percentage`() {

    val result = factory.create(weatherMetrics(precipitationProbability = 60))

    expectThat(result.conditions[0].value).isEqualTo("60%")
  }

  @Test
  fun `when details created, then map uv index label`() {

    val result = factory.create(METRICS)

    expectThat(result.conditions[1].label).isEqualTo("UV Index")
  }

  @Test
  fun `when details created, then map visibility in km for high values`() {

    val result = factory.create(weatherMetrics(visibility = 10000.0))

    expectThat(result.conditions[2].value).isEqualTo("10 km")
  }

  @Test
  fun `when details created, then map visibility in meters for low values`() {

    val result = factory.create(weatherMetrics(visibility = 500.0))

    expectThat(result.conditions[2].value).isEqualTo("500 m")
  }

  @Test
  fun `when details created, then map rainfall label`() {

    val result = factory.create(METRICS)

    expectThat(result.conditions[3].label).isEqualTo("Rainfall")
  }

  @Test
  fun `when details created, then preview has four items`() {

    val result = factory.create(METRICS)

    expectThat(result.previewItems).hasSize(4)
  }

  @Test
  fun `when details created, then preview contains humidity from atmosphere`() {

    val result = factory.create(METRICS)

    expectThat(result.previewItems[0]).isEqualTo(result.atmosphere[0])
  }

  @Test
  fun `when details created, then preview contains wind speed from wind`() {

    val result = factory.create(METRICS)

    expectThat(result.previewItems[1]).isEqualTo(result.wind[0])
  }

  @Test
  fun `when details created, then preview contains uv index from conditions`() {

    val result = factory.create(METRICS)

    expectThat(result.previewItems[2]).isEqualTo(result.conditions[1])
  }

  @Test
  fun `when details created, then preview contains precipitation from conditions`() {

    val result = factory.create(METRICS)

    expectThat(result.previewItems[3]).isEqualTo(result.conditions[0])
  }
}
