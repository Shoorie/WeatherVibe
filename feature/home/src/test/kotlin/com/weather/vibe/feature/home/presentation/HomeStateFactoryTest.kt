package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.usecase.ConvertTemperature
import com.weather.vibe.feature.home.presentation.fake.FakeTimeProvider
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.presentation.fixture.MetricFixtures.METRICS_SECTIONS
import com.weather.vibe.feature.home.presentation.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.feature.home.presentation.fixture.WeatherDataFixtures.weatherData
import com.weather.vibe.feature.home.presentation.fixture.WeatherSuggestionFixtures.SUGGESTION
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

  private val convertTemperature = mockk<ConvertTemperature>()
  private val resources: HomeResources = fakeHomeResources()
  private val metricsFactory = mockk<MetricsStateFactory>()
  private val playlistFactory = PlaylistStateFactory()
  private val fakeTimeProvider = FakeTimeProvider()

  private val sunriseSunsetFactory = SunriseSunsetStateFactory(
    resources = resources,
    timeProvider = fakeTimeProvider
  )

  private val factory: HomeStateFactory = HomeStateFactory(
    convertTemperature = convertTemperature,
    metricsFactory = metricsFactory,
    playlistFactory = playlistFactory,
    resources = resources,
    sunriseSunsetFactory = sunriseSunsetFactory
  )

  @Before
  fun setUp() {
    every { convertTemperature(celsius = any(), unit = any()) } answers {
      "${firstArg<Double>().toInt()}°"
    }
    every { metricsFactory.create(any(), any()) } returns METRICS_SECTIONS
  }

  @After
  fun tearDown() {
    unmockkAll()
  }


  @Test
  fun `when state created, then map city name to header`() {

    val result = factory.create(WEATHER)

    expectThat(result.header.cityName).isEqualTo("Warsaw")
  }

  @Test
  fun `when state created, then header date label is not empty`() {

    val result = factory.create(WEATHER)

    expectThat(result.header.dateLabel.isNotBlank()).isTrue()
  }

  @Test
  fun `when state created, then map current temperature`() {

    val result = factory.create(WEATHER)

    expectThat(result.currentWeather.currentTemperature)
      .isEqualTo("22°")
  }

  @Test
  fun `when state created, then map feels like temperature`() {

    val result = factory.create(WEATHER)

    expectThat(result.currentWeather.feelsLikeTemperature)
      .isEqualTo("20°")
  }

  @Test
  fun `when state created, then map high temperature from daily forecast`() {

    val result = factory.create(WEATHER)

    expectThat(result.currentWeather.highTemperature)
      .isEqualTo("25°")
  }

  @Test
  fun `when state created, then map low temperature from daily forecast`() {

    val result = factory.create(WEATHER)

    expectThat(result.currentWeather.lowTemperature)
      .isEqualTo("12°")
  }

  @Test
  fun `when state created, then map condition emoji`() {

    val result = factory.create(WEATHER)

    expectThat(result.currentWeather.conditionEmoji)
      .isEqualTo(CLEAR_SKY.emoji)
  }

  @Test
  fun `when state created, then map condition label`() {

    val result = factory.create(WEATHER)

    expectThat(result.currentWeather.conditionLabel)
      .isEqualTo(CLEAR_SKY.label)
  }

  @Test
  fun `given no daily forecast, when state created, then use current temp as high`() {

    val data = weatherData(dailyForecast = emptyList())

    val result = factory.create(data)

    expectThat(result.currentWeather.highTemperature)
      .isEqualTo("22°")
  }

  @Test
  fun `given no daily forecast, when state created, then use current temp as low`() {

    val data = weatherData(dailyForecast = emptyList())

    val result = factory.create(data)

    expectThat(result.currentWeather.lowTemperature)
      .isEqualTo("22°")
  }


  @Test
  fun `when state created, then map hourly forecast items`() {

    val result = factory.create(WEATHER)

    expectThat(result.hourlyForecast).map { it.timeLabel }
      .containsExactly("12:00", "13:00", "14:00")
  }

  @Test
  fun `when state created, then first hour is marked as current`() {

    val result = factory.create(WEATHER)

    expectThat(result.hourlyForecast[0].isCurrentHour).isTrue()
  }

  @Test
  fun `when state created, then non-first hours are not marked as current`() {

    val result = factory.create(WEATHER)

    expectThat(result.hourlyForecast.drop(1)).map { it.isCurrentHour }
      .containsExactly(false, false)
  }

  @Test
  fun `when state created, then map hourly condition emojis`() {

    val result = factory.create(WEATHER)

    expectThat(result.hourlyForecast).map { it.conditionEmoji }
      .containsExactly(CLEAR_SKY.emoji, CLEAR_SKY.emoji, PARTLY_CLOUDY.emoji)
  }

  @Test
  fun `when state created, then map hourly temperatures`() {

    val result = factory.create(WEATHER)

    expectThat(result.hourlyForecast).map { it.temperature }
      .containsExactly("22°", "24°", "23°")
  }


  @Test
  fun `when state created, then map daily forecast condition emojis`() {

    val result = factory.create(WEATHER)

    expectThat(result.dailyForecast).map { it.conditionEmoji }
      .containsExactly(CLEAR_SKY.emoji, PARTLY_CLOUDY.emoji, RAIN.emoji)
  }

  @Test
  fun `when state created, then map daily max temperatures`() {

    val result = factory.create(WEATHER)

    expectThat(result.dailyForecast[0].maxTemperature)
      .isEqualTo("25°")
  }

  @Test
  fun `when state created, then map daily min temperatures`() {

    val result = factory.create(WEATHER)

    expectThat(result.dailyForecast[0].minTemperature)
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
      .get { this.briefing }.isEqualTo(briefing)
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
      .get { this.playlist }.isEqualTo(playlist)
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
      temperatureUnit = FAHRENHEIT
    )

    expectThat(result).isA<HomeUiState.Loaded>()
  }

  @Test
  fun `given loaded state with briefing, when temperatures reformatted, then preserve briefing`() {

    val briefing = BriefingUiState.Loaded("Sunny!")
    val loaded = factory.create(WEATHER).copy(briefing = briefing)

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      temperatureUnit = CELSIUS
    ) as HomeUiState.Loaded

    expectThat(result.briefing).isEqualTo(briefing)
  }

  @Test
  fun `given loaded state with playlist, when temperatures reformatted, then preserve playlist`() {

    val playlist = playlistFactory.create(SUGGESTION)
    val loaded = factory.create(WEATHER).copy(playlist = playlist)

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      temperatureUnit = CELSIUS
    ) as HomeUiState.Loaded

    expectThat(result.playlist).isEqualTo(playlist)
  }

  @Test
  fun `given loading state, when temperatures reformatted, then return unchanged`() {

    val result = factory.reformatTemperatures(
      current = HomeUiState.Loading,
      data = WEATHER,
      temperatureUnit = CELSIUS
    )

    expectThat(result).isA<HomeUiState.Loading>()
  }


  @Test
  fun `when playlist created, then delegate to playlist factory`() {

    val result = factory.createPlaylist(SUGGESTION)

    expectThat(result.mood).isEqualTo("Uplifting")
  }
}
