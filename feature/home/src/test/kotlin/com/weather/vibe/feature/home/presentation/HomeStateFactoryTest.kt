package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.domain.weather.usecase.BuildPlaylistQuery
import com.weather.vibe.feature.home.presentation.factory.AiSuggestionSectionFactory
import com.weather.vibe.feature.home.presentation.factory.EnvironmentSectionFactory
import com.weather.vibe.feature.home.presentation.factory.ForecastSectionFactory
import com.weather.vibe.feature.home.presentation.factory.HomeFactories
import com.weather.vibe.feature.home.presentation.factory.HomeStateFactory
import com.weather.vibe.feature.home.presentation.factory.MetricsStateFactory
import com.weather.vibe.feature.home.presentation.factory.PlaylistStateFactory
import com.weather.vibe.feature.home.presentation.factory.SharePosterFactory
import com.weather.vibe.feature.home.presentation.fixture.MetricFixtures.METRICS_SECTIONS
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.forecastSection
import com.weather.vibe.testing.viberating.fixture.WeatherSnapshotFixtures
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.METRICS
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class HomeStateFactoryTest {

  private val forecastFactory = mockk<ForecastSectionFactory>()
  private val metricsFactory = mockk<MetricsStateFactory>()
  private val playlistFactory = PlaylistStateFactory(buildPlaylistQuery = BuildPlaylistQuery())

  private val factories = HomeFactories(
    aiSuggestion = mockk<AiSuggestionSectionFactory>(),
    environment = mockk<EnvironmentSectionFactory>(),
    forecast = forecastFactory,
    metrics = metricsFactory,
    sharePoster = mockk<SharePosterFactory>()
  )

  private val factory = HomeStateFactory(factories = factories)

  @Before
  fun setUp() {
    every { metricsFactory.create(any(), any()) } returns METRICS_SECTIONS
    every { forecastFactory.create(any(), any()) } returns forecastSection
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when temperatures reformatted on loaded state, then return loaded`() {

    val loaded = factory.create(
      data = WEATHER,
      metrics = METRICS,
      vibeSnapshot = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C
    )

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      metrics = METRICS,
      unit = FAHRENHEIT
    )

    expectThat(result).isA<HomeUiState.Loaded>()
  }

  @Test
  fun `given loaded state with briefing, when temperatures reformatted, then preserve briefing`() {

    val briefing = BriefingUiState.Loaded("Sunny!")
    val created = factory.create(
      data = WEATHER,
      metrics = METRICS,
      vibeSnapshot = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C
    )
    val loaded = created.copy(aiSuggestion = created.aiSuggestion.copy(briefing = briefing))

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      metrics = METRICS,
      unit = CELSIUS
    ) as HomeUiState.Loaded

    expectThat(result.aiSuggestion.briefing).isEqualTo(briefing)
  }

  @Test
  fun `given loaded state with playlist, when temperatures reformatted, then preserve playlist`() {

    val playlist = playlistFactory.create(WeatherSuggestionFixtures.SUGGESTION)
    val created = factory.create(
      data = WEATHER,
      metrics = METRICS,
      vibeSnapshot = WeatherSnapshotFixtures.PARTLY_CLOUDY_18C
    )
    val loaded = created.copy(aiSuggestion = created.aiSuggestion.copy(playlist = playlist))

    val result = factory.reformatTemperatures(
      current = loaded,
      data = WEATHER,
      metrics = METRICS,
      unit = CELSIUS
    ) as HomeUiState.Loaded

    expectThat(result.aiSuggestion.playlist).isEqualTo(playlist)
  }

  @Test
  fun `when temperatures reformatted on loading state, then return unchanged`() {

    val result = factory.reformatTemperatures(
      current = HomeUiState.Loading,
      data = WEATHER,
      metrics = METRICS,
      unit = CELSIUS
    )

    expectThat(result).isA<HomeUiState.Loading>()
  }
}
