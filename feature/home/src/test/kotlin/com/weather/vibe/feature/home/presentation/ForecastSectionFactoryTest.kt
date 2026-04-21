package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.DailyTemperatureRange.Companion.emptyFor
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.usecase.BuildDailyTemperatureRanges
import com.weather.vibe.domain.weather.usecase.CalculateDayLength
import com.weather.vibe.domain.weather.usecase.CalculateSunProgress
import com.weather.vibe.domain.weather.usecase.FindCurrentHourIndex
import com.weather.vibe.domain.weather.usecase.ResolveTodaySunInfo
import com.weather.vibe.domain.weather.usecase.ResolveTodayTemperatureBounds
import com.weather.vibe.feature.home.presentation.factory.ForecastSectionFactory
import com.weather.vibe.feature.home.presentation.factory.ForecastUseCases
import com.weather.vibe.feature.home.presentation.factory.SunriseSunsetStateFactory
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue
import strikt.assertions.map

class ForecastSectionFactoryTest {

  private val temperature = mockk<TemperatureFormatter>()
  private val buildDailyTemperatureRanges = mockk<BuildDailyTemperatureRanges>()
  private val resources: HomeResources = fakeHomeResources()
  private val fakeTimeProvider = FakeTimeProvider()

  private val sunriseSunsetFactory = SunriseSunsetStateFactory(resources = resources)

  private val resolveTodaySunInfo = ResolveTodaySunInfo(
    calculateDayLength = CalculateDayLength(),
    calculateSunProgress = CalculateSunProgress(timeProvider = fakeTimeProvider)
  )

  private val useCases = ForecastUseCases(
    buildDailyTemperatureRanges = buildDailyTemperatureRanges,
    findCurrentHourIndex = FindCurrentHourIndex(timeProvider = fakeTimeProvider),
    resolveTodaySunInfo = resolveTodaySunInfo,
    resolveTodayTemperatureBounds = ResolveTodayTemperatureBounds()
  )

  private val factory = ForecastSectionFactory(
    resources = resources,
    sunriseSunsetFactory = sunriseSunsetFactory,
    temperature = temperature,
    timeProvider = fakeTimeProvider,
    useCases = useCases
  )

  @Before
  fun setUp() {
    every { temperature.format(celsius = any(), unit = any()) } answers {
      "${firstArg<Double>().toInt()}°"
    }
    every {
      buildDailyTemperatureRanges(
        days = any(),
        currentTemperatureCelsius = any(),
        unit = any(),
        today = any()
      )
    } answers {
      firstArg<List<DailyWeather>>().map { day -> emptyFor(day.date) }
    }
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when section created, then header contains city name`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.header.cityName).isEqualTo("Warsaw")
  }

  @Test
  fun `when section created, then header date label is not empty`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.header.dateLabel.isNotBlank()).isTrue()
  }

  @Test
  fun `when section created, then current temperature formatted`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.currentWeather.currentTemperature).isEqualTo("22°")
  }

  @Test
  fun `when section created, then feels like temperature formatted`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.currentWeather.feelsLikeTemperature).isEqualTo("20°")
  }

  @Test
  fun `when section created, then high temperature taken from first daily forecast`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.currentWeather.highTemperature).isEqualTo("25°")
  }

  @Test
  fun `when section created, then low temperature taken from first daily forecast`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.currentWeather.lowTemperature).isEqualTo("12°")
  }

  @Test
  fun `when section created, then current weather shows condition emoji`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.currentWeather.conditionEmoji).isEqualTo(CLEAR_SKY.emoji)
  }

  @Test
  fun `given clear sky at night, then current weather shows moon emoji`() {

    val nightWeather = weatherData(isDay = false)
    val nightEmoji = CLEAR_SKY.emojiAt(isDay = false)

    val result = factory.create(nightWeather, CELSIUS)

    expectThat(result.currentWeather.conditionEmoji).isEqualTo(nightEmoji)
  }

  @Test
  fun `when section created, then current weather shows condition label`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.currentWeather.conditionLabel).isEqualTo(CLEAR_SKY.label)
  }

  @Test
  fun `given no daily forecast, when section created, then use current temp as high`() {

    val data = weatherData(dailyForecast = emptyList())

    val result = factory.create(data, CELSIUS)

    expectThat(result.currentWeather.highTemperature).isEqualTo("22°")
  }

  @Test
  fun `given no daily forecast, when section created, then use current temp as low`() {

    val data = weatherData(dailyForecast = emptyList())

    val result = factory.create(data, CELSIUS)

    expectThat(result.currentWeather.lowTemperature).isEqualTo("22°")
  }

  @Test
  fun `when section created, then hourly forecast shows time labels`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.hourlyForecast.items).map { it.timeLabel }
      .containsExactly("Now", "13:00", "14:00")
  }

  @Test
  fun `when section created, then first hour is marked as current`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.hourlyForecast.items[0].isCurrentHour).isTrue()
  }

  @Test
  fun `when section created, then non-first hours are not marked as current`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.hourlyForecast.items.drop(1)).map { it.isCurrentHour }
      .containsExactly(false, false)
  }

  @Test
  fun `when section created, then hourly forecast shows condition emojis`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.hourlyForecast.items).map { it.conditionEmoji }
      .containsExactly(CLEAR_SKY.emoji, CLEAR_SKY.emoji, PARTLY_CLOUDY.emoji)
  }

  @Test
  fun `when section created, then hourly forecast shows temperatures`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.hourlyForecast.items).map { it.temperature }
      .containsExactly("22°", "24°", "23°")
  }

  @Test
  fun `when section created, then daily forecast shows condition emojis`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.dailyForecast.items).map { it.conditionEmoji }
      .containsExactly(CLEAR_SKY.emoji, PARTLY_CLOUDY.emoji, RAIN.emoji)
  }

  @Test
  fun `when section created, then daily forecast shows max temperature`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.dailyForecast.items[0].maxTemperature).isEqualTo("25°")
  }

  @Test
  fun `when section created, then daily forecast shows min temperature`() {

    val result = factory.create(WEATHER, CELSIUS)

    expectThat(result.dailyForecast.items[0].minTemperature).isEqualTo("12°")
  }
}
