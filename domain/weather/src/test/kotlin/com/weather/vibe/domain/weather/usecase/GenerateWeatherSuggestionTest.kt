package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.premium.usecase.CanGenerateBrief
import com.weather.vibe.domain.weather.model.WeatherBriefResult
import com.weather.vibe.domain.weather.model.WeatherBriefResult.LimitReached
import com.weather.vibe.domain.weather.model.WeatherBriefResult.Ready
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.DEFAULT_WEATHER_KEY
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GenerateWeatherSuggestionTest {

  private val canGenerateBrief = mockk<CanGenerateBrief>()
  private val fetchWeatherSuggestion = mockk<FetchWeatherSuggestion>()
  private val getCachedWeatherSuggestion = mockk<GetCachedWeatherSuggestion>()

  private var cached: WeatherSuggestion? = null

  private val generateWeatherSuggestion = GenerateWeatherSuggestion(
    canGenerateBrief = canGenerateBrief,
    fetchWeatherSuggestion = fetchWeatherSuggestion,
    generationLock = Mutex(),
    getCachedWeatherSuggestion = getCachedWeatherSuggestion
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given two concurrent requests for same inputs, when generated, then suggestion is fetched once`() = runTest {

    stubReadyToGenerate()
    val gate = CompletableDeferred<Unit>()
    coEvery { fetchWeatherSuggestion(any(), any()) } coAnswers {
      gate.await()
      cached = SUGGESTION
      SUGGESTION
    }

    val first = backgroundScope.async { suggestionFor().first() }
    runCurrent()
    val second = backgroundScope.async { suggestionFor().first() }
    runCurrent()
    gate.complete(Unit)
    advanceUntilIdle()

    expectThat(first.await().suggestion()).isEqualTo(SUGGESTION)
    expectThat(second.await().suggestion()).isEqualTo(SUGGESTION)
    coVerify(exactly = 1) { fetchWeatherSuggestion(any(), any()) }
  }

  @Test
  fun `given a valid cached suggestion, when requested, then nothing is fetched`() = runTest {

    stubReadyToGenerate()
    cached = SUGGESTION

    val result = suggestionFor().first()

    expectThat(result.suggestion()).isEqualTo(SUGGESTION)
    coVerify(exactly = 0) { fetchWeatherSuggestion(any(), any()) }
  }

  @Test
  fun `given selected tone not accessible and no cache, when requested, then limit reached`() = runTest {

    stubReadyToGenerate()
    coEvery { canGenerateBrief() } returns false

    val result = suggestionFor().first()

    expectThat(result.getOrThrow()).isA<LimitReached>()
    coVerify(exactly = 0) { fetchWeatherSuggestion(any(), any()) }
  }

  private fun stubReadyToGenerate() {
    coEvery { getCachedWeatherSuggestion(any(), any()) } answers { cached }
    coEvery { canGenerateBrief() } returns true
    coEvery { fetchWeatherSuggestion(any(), any()) } coAnswers {
      cached = SUGGESTION
      SUGGESTION
    }
  }

  private fun Result<WeatherBriefResult>.suggestion(): WeatherSuggestion? =
    (getOrNull() as? Ready)?.suggestion

  private fun suggestionFor() =
    generateWeatherSuggestion(
      weatherData = WEATHER,
      weatherKey = DEFAULT_WEATHER_KEY
    )
}
