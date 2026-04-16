package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.IsMorningBriefEnabled
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER_KEY
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class GetMorningBriefTextTest {

  private val generateWeatherSuggestion = mockk<GenerateWeatherSuggestion>()
  private val getCurrentWeatherKey = mockk<GetCurrentWeatherKey>()
  private val getWeather = mockk<GetWeather>()
  private val isMorningBriefEnabled = mockk<IsMorningBriefEnabled>()
  private val observeCurrentLocation = mockk<ObserveCurrentLocation>()
  private val getMorningBriefText = GetMorningBriefText(
    generateWeatherSuggestion = generateWeatherSuggestion,
    getCurrentWeatherKey = getCurrentWeatherKey,
    getWeather = getWeather,
    isMorningBriefEnabled = isMorningBriefEnabled,
    observeCurrentLocation = observeCurrentLocation
  )

  @Before
  fun setUp() {
    coEvery { isMorningBriefEnabled() } returns true
    every { observeCurrentLocation() } returns flowOf(WARSAW)
    every { getWeather(WARSAW.toCoordinates()) } returns flowOf(success(WEATHER))
    every { getCurrentWeatherKey(WEATHER) } returns WEATHER_KEY
    every { generateWeatherSuggestion(WEATHER, WEATHER_KEY) } returns
      flowOf(success(SUGGESTION))
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when invoked, then suggestion brief text returned`() = runTest {

    expectThat(getMorningBriefText()).isEqualTo(SUGGESTION.briefText)
  }

  @Test
  fun `given morning brief disabled, when invoked, then null returned`() = runTest {

    coEvery { isMorningBriefEnabled() } returns false

    expectThat(getMorningBriefText()).isNull()
  }

  @Test
  fun `given no current location, when invoked, then null returned`() = runTest {

    every { observeCurrentLocation() } returns flowOf(null)

    expectThat(getMorningBriefText()).isNull()
  }

  @Test
  fun `given weather fetch fails, when invoked, then error propagates`() = runTest {

    every { getWeather(WARSAW.toCoordinates()) } returns
      flowOf(failure(IllegalStateException("offline")))

    expectThrows<IllegalStateException> { getMorningBriefText() }
  }

  @Test
  fun `given suggestion fails, when invoked, then error propagates`() = runTest {

    every { generateWeatherSuggestion(WEATHER, WEATHER_KEY) } returns
      flowOf(failure(IllegalStateException("ai down")))

    expectThrows<IllegalStateException> { getMorningBriefText() }
  }
}
