package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.format.TemperatureFormatter
import com.weather.vibe.domain.weather.model.DailyTemperatureRange.Companion.emptyFor
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.usecase.BuildDailyTemperatureRanges
import com.weather.vibe.domain.weather.usecase.BuildPlaylistQuery
import com.weather.vibe.domain.weather.usecase.CalculateDayLength
import com.weather.vibe.domain.weather.usecase.CalculateSunProgress
import com.weather.vibe.domain.weather.usecase.ComputeWindDirection
import com.weather.vibe.domain.weather.usecase.FindCurrentHourIndex
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherMetrics
import com.weather.vibe.domain.weather.usecase.ResolveTodaySunInfo
import com.weather.vibe.domain.weather.usecase.ResolveTodayTemperatureBounds
import com.weather.vibe.feature.home.presentation.factory.AirQualityStateFactory
import com.weather.vibe.feature.home.presentation.factory.HomeFactories
import com.weather.vibe.feature.home.presentation.fake.fakeHomeAirQualityResources
import com.weather.vibe.feature.home.presentation.factory.HomeFactoryUseCases
import com.weather.vibe.feature.home.presentation.factory.HomeStateFactory
import com.weather.vibe.feature.home.presentation.factory.MetricsStateFactory
import com.weather.vibe.feature.home.presentation.factory.PlaylistStateFactory
import com.weather.vibe.feature.home.presentation.factory.SunriseSunsetStateFactory
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.presentation.fixture.MetricFixtures.METRICS_SECTIONS
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.ui.HomeResources
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue
import strikt.assertions.map

class HomeStateFactoryTest {

  private val temperature = mockk<TemperatureFormatter>()
  private val buildDailyTemperatureRanges = mockk<BuildDailyTemperatureRanges>()
  private val resources: HomeResources = fakeHomeResources()
  private val metricsFactory = mockk<MetricsStateFactory>()
  private val playlistFactory = PlaylistStateFactory(
    buildPlaylistQuery = BuildPlaylistQuery()
  )
  private val fakeTimeProvider = FakeTimeProvider()

  private val sunriseSunsetFactory = SunriseSunsetStateFactory(resources = resources)
  private val airQualityFactory = AirQualityStateFactory(
    resources = fakeHomeAirQualityResources()
  )

  private val factories = HomeFactories(
    airQuality = airQualityFactory,
    metrics = metricsFactory,
    playlist = playlistFactory,
    sunriseSunset = sunriseSunsetFactory
  )

  private val resolveTodaySunInfo = ResolveTodaySunInfo(
    calculateDayLength = CalculateDayLength(),
    calculateSunProgress = CalculateSunProgress(timeProvider = fakeTimeProvider)
  )

  private val getCurrentWeatherMetrics = GetCurrentWeatherMetrics(
    computeWindDirection = ComputeWindDirection(),
    findCurrentHourIndex = FindCurrentHourIndex(timeProvider = fakeTimeProvider)
  )

  private val factoryUseCases = HomeFactoryUseCases(
    buildDailyTemperatureRanges = buildDailyTemperatureRanges,
    findCurrentHourIndex = FindCurrentHourIndex(timeProvider = fakeTimeProvider),
    getCurrentWeatherMetrics = getCurrentWeatherMetrics,
    resolveTodaySunInfo = resolveTodaySunInfo,
    resolveTodayTemperatureBounds = ResolveTodayTemperatureBounds()
  )

  private val factory: HomeStateFactory = HomeStateFactory(
    factories = factories,
    resources = resources,
    temperature = temperature,
    timeProvider = fakeTimeProvider,
    useCases = factoryUseCases
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
    every { metricsFactory.create(any(), any()) } returns METRICS_SECTIONS
  }

  @After
  fun tearDown() {
    unmockkAll()
  }


  @Test
  fun `when state created, then header contains city name`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.header.cityName).isEqualTo("Warsaw")
  }

  @Test
  fun `when state created, then header date label is not empty`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.header.dateLabel.isNotBlank()).isTrue()
  }

  @Test
  fun `when state created, then current temperature formatted`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.currentWeather.currentTemperature)
      .isEqualTo("22°")
  }

  @Test
  fun `when state created, then feels like temperature formatted`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.currentWeather.feelsLikeTemperature)
      .isEqualTo("20°")
  }

  @Test
  fun `when state created, then high temperature taken from first daily forecast`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.currentWeather.highTemperature)
      .isEqualTo("25°")
  }

  @Test
  fun `when state created, then low temperature taken from first daily forecast`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.currentWeather.lowTemperature)
      .isEqualTo("12°")
  }

  @Test
  fun `when state created, then current weather shows condition emoji`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.currentWeather.conditionEmoji)
      .isEqualTo(CLEAR_SKY.emoji)
  }

  @Test
  fun `when state created, then current weather shows condition label`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.currentWeather.conditionLabel)
      .isEqualTo(CLEAR_SKY.label)
  }

  @Test
  fun `given no daily forecast, when state created, then use current temp as high`() {

    val data = weatherData(dailyForecast = emptyList())

    val result = factory.create(data)

    expectThat(result.forecast.currentWeather.highTemperature)
      .isEqualTo("22°")
  }

  @Test
  fun `given no daily forecast, when state created, then use current temp as low`() {

    val data = weatherData(dailyForecast = emptyList())

    val result = factory.create(data)

    expectThat(result.forecast.currentWeather.lowTemperature)
      .isEqualTo("22°")
  }


  @Test
  fun `when state created, then hourly forecast shows time labels`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.hourlyForecast.items).map { it.timeLabel }
      .containsExactly("Now", "13:00", "14:00")
  }

  @Test
  fun `when state created, then first hour is marked as current`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.hourlyForecast.items[0].isCurrentHour).isTrue()
  }

  @Test
  fun `when state created, then non-first hours are not marked as current`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.hourlyForecast.items.drop(1)).map { it.isCurrentHour }
      .containsExactly(false, false)
  }

  @Test
  fun `when state created, then hourly forecast shows condition emojis`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.hourlyForecast.items).map { it.conditionEmoji }
      .containsExactly(CLEAR_SKY.emoji, CLEAR_SKY.emoji, PARTLY_CLOUDY.emoji)
  }

  @Test
  fun `when state created, then hourly forecast shows temperatures`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.hourlyForecast.items).map { it.temperature }
      .containsExactly("22°", "24°", "23°")
  }


  @Test
  fun `when state created, then daily forecast shows condition emojis`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.dailyForecast.items).map { it.conditionEmoji }
      .containsExactly(CLEAR_SKY.emoji, PARTLY_CLOUDY.emoji, RAIN.emoji)
  }

  @Test
  fun `when state created, then daily forecast shows max temperature`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.dailyForecast.items[0].maxTemperature)
      .isEqualTo("25°")
  }

  @Test
  fun `when state created, then daily forecast shows min temperature`() {

    val result = factory.create(WEATHER)

    expectThat(result.forecast.dailyForecast.items[0].minTemperature)
      .isEqualTo("12°")
  }


  @Test
  fun `given loaded state, when suggestion applied, then update briefing`() {

    val loaded = factory.create(WEATHER)
    val briefing = BriefingUiState.Loaded("Sunny day!")

    val result = factory.applyWeatherSuggestion(
      briefing = briefing,
      current = loaded,
      playlist = PlaylistUiState.Loading
    )

    expectThat(result).isA<HomeUiState.Loaded>()
      .get { aiSuggestion.briefing }.isEqualTo(briefing)
  }

  @Test
  fun `given loaded state, when suggestion applied, then update playlist`() {

    val loaded = factory.create(WEATHER)
    val playlist = playlistFactory.create(SUGGESTION)

    val result = factory.applyWeatherSuggestion(
      briefing = BriefingUiState.Loading,
      current = loaded,
      playlist = playlist
    )

    expectThat(result).isA<HomeUiState.Loaded>()
      .get { aiSuggestion.playlist }.isEqualTo(playlist)
  }

  @Test
  fun `given loading state, when suggestion applied, then return unchanged`() {

    val result = factory.applyWeatherSuggestion(
      briefing = BriefingUiState.Loaded("text"),
      current = HomeUiState.Loading,
      playlist = PlaylistUiState.Loading
    )

    expectThat(result).isA<HomeUiState.Loading>()
  }

  @Test
  fun `given error state, when suggestion applied, then return unchanged`() {

    val result = factory.applyWeatherSuggestion(
      briefing = BriefingUiState.Loaded("text"),
      current = HomeUiState.Error("Something went wrong"),
      playlist = PlaylistUiState.Loading
    )

    expectThat(result).isA<HomeUiState.Error>()
  }


  @Test
  fun `given loaded state, when temperatures reformatted, then return loaded`() {

    val loaded = factory.create(WEATHER)

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      unit = FAHRENHEIT
    )

    expectThat(result).isA<HomeUiState.Loaded>()
  }

  @Test
  fun `given loaded state with briefing, when temperatures reformatted, then preserve briefing`() {

    val briefing = BriefingUiState.Loaded("Sunny!")
    val created = factory.create(WEATHER)
    val loaded = created.copy(aiSuggestion = created.aiSuggestion.copy(briefing = briefing))

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      unit = CELSIUS
    ) as HomeUiState.Loaded

    expectThat(result.aiSuggestion.briefing).isEqualTo(briefing)
  }

  @Test
  fun `given loaded state with playlist, when temperatures reformatted, then preserve playlist`() {

    val playlist = playlistFactory.create(SUGGESTION)
    val created = factory.create(WEATHER)
    val loaded = created.copy(aiSuggestion = created.aiSuggestion.copy(playlist = playlist))

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      unit = CELSIUS
    ) as HomeUiState.Loaded

    expectThat(result.aiSuggestion.playlist).isEqualTo(playlist)
  }

  @Test
  fun `given loading state, when temperatures reformatted, then return unchanged`() {

    val result = factory.reformatTemperatures(
      current = HomeUiState.Loading,
      data = WEATHER,
      unit = CELSIUS
    )

    expectThat(result).isA<HomeUiState.Loading>()
  }


  @Test
  fun `when playlist created, then delegate to playlist factory`() {

    val result = factory.createPlaylist(SUGGESTION)

    expectThat(result.mood).isEqualTo("Uplifting")
  }

  @Test
  fun `when briefing created, then text taken from suggestion`() {

    val result = factory.createBriefing(suggestion = SUGGESTION)

    expectThat(result.text).isEqualTo("Beautiful sunny day, perfect for a walk!")
  }

  @Test
  fun `when briefing created, then outfit taken from suggestion`() {

    val result = factory.createBriefing(suggestion = SUGGESTION)

    expectThat(result.outfit).isEqualTo("T-shirt, sunglasses, light cap")
  }
}
