package com.weather.vibe.feature.home.presentation

import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW
import com.weather.vibe.domain.weather.model.WeatherCondition.THUNDERSTORM
import com.weather.vibe.domain.weather.usecase.ResolveWeatherVibeKey
import com.weather.vibe.feature.home.presentation.factory.SharePosterFactory
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SharePosterFactoryTest {

  private val temperature = mockk<TemperatureFormatter>()
  private val resources: HomeResources = fakeHomeResources()
  private val fakeTimeProvider = FakeTimeProvider()

  private val factory = SharePosterFactory(
    resolveWeatherVibeKey = ResolveWeatherVibeKey(),
    resources = resources,
    temperature = temperature,
    timeProvider = fakeTimeProvider
  )

  @Before
  fun setUp() {
    every { temperature.format(celsius = any(), unit = any()) } answers {
      "${firstArg<Double>().toInt()}°"
    }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given vibe one-liner provided, when share poster created, then quote text uses one-liner`() {

    val oneLiner = "Perfect — get out there."

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = oneLiner,
      weather = WEATHER
    )

    expectThat(result.quoteText).isEqualTo(oneLiner)
  }

  @Test
  fun `given no vibe one-liner, when share poster created, then quote text falls back to brief text`() {

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = WEATHER
    )

    expectThat(result.quoteText).isEqualTo("Beautiful sunny day, perfect for a walk!")
  }

  @Test
  fun `when share poster created, then it contains city name`() {

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = WEATHER
    )

    expectThat(result.cityName).isEqualTo("Warsaw")
  }

  @Test
  fun `when share poster created, then temperature formatted from current temperature`() {

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = WEATHER
    )

    expectThat(result.temperature).isEqualTo("22°")
  }

  @Test
  fun `given clear sky during day, when share poster created, then gradient is sunny`() {

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = WEATHER
    )

    expectThat(result.gradientKey).isEqualTo(ShareGradientKey.SUNNY)
  }

  @Test
  fun `given clear sky at night, when share poster created, then gradient is night`() {

    val nightWeather = weatherData(isDay = false)

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = nightWeather
    )

    expectThat(result.gradientKey).isEqualTo(ShareGradientKey.NIGHT)
  }

  @Test
  fun `given rain during day, when share poster created, then gradient is rainy`() {

    val rainyWeather = weatherData(condition = RAIN)

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = rainyWeather
    )

    expectThat(result.gradientKey).isEqualTo(ShareGradientKey.RAINY)
  }

  @Test
  fun `given snow during day, when share poster created, then gradient is snowy`() {

    val snowyWeather = weatherData(condition = SNOW)

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = snowyWeather
    )

    expectThat(result.gradientKey).isEqualTo(ShareGradientKey.SNOWY)
  }

  @Test
  fun `given thunderstorm during day, when share poster created, then gradient is stormy`() {

    val stormyWeather = weatherData(condition = THUNDERSTORM)

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = stormyWeather
    )

    expectThat(result.gradientKey).isEqualTo(ShareGradientKey.STORMY)
  }

  @Test
  fun `given partly cloudy during day, when share poster created, then gradient is cloudy`() {

    val cloudyWeather = weatherData(condition = PARTLY_CLOUDY)

    val result = factory.create(
      suggestion = SUGGESTION,
      unit = CELSIUS,
      vibeOneLiner = null,
      weather = cloudyWeather
    )

    expectThat(result.gradientKey).isEqualTo(ShareGradientKey.CLOUDY)
  }
}
