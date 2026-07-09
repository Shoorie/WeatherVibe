package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.usecase.AddToGenreHistory
import com.weather.vibe.domain.settings.usecase.ObserveUserSettings
import com.weather.vibe.domain.weather.cache.WeatherSuggestionCache
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.UserDispositionEntry
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.repository.WeatherSuggestionRepository
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.DEFAULT_WEATHER_KEY
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.cachedSuggestion
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GenerateWeatherSuggestionTest {

  private val addToGenreHistory = mockk<AddToGenreHistory>(relaxed = true)
  private val buildWeatherSuggestionPrompt = mockk<BuildWeatherSuggestionPrompt>()
  private val observeUserSettings = mockk<ObserveUserSettings>()
  private val repository = mockk<WeatherSuggestionRepository>()

  private var cached: CachedWeatherSuggestion? = null
  private val cache = mockk<WeatherSuggestionCache> {
    coEvery { get(any(), any(), any(), any(), any()) } answers { cached }
    coEvery { save(any(), any(), any(), any(), any(), any()) } answers {
      cached = cachedSuggestion(
        fetchedAt = System.currentTimeMillis(),
        suggestion = arg<WeatherSuggestion>(3)
      )
    }
  }

  private val generateWeatherSuggestion = GenerateWeatherSuggestion(
    addToGenreHistory = addToGenreHistory,
    buildWeatherSuggestionPrompt = buildWeatherSuggestionPrompt,
    cache = cache,
    generationLock = Mutex(),
    observeUserSettings = observeUserSettings,
    repository = repository,
    timeProvider = FakeTimeProvider()
  )

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given two concurrent requests for same inputs, when generated, then suggestion is fetched once`() = runTest {

    stubSettingsAndPrompt()
    val gate = CompletableDeferred<Unit>()
    coEvery { repository.getSuggestionBasedOn(any()) } coAnswers {
      gate.await()
      SUGGESTION
    }

    val first = backgroundScope.async { suggestionFor().first() }
    runCurrent()
    val second = backgroundScope.async { suggestionFor().first() }
    runCurrent()
    gate.complete(Unit)
    advanceUntilIdle()

    expectThat(first.await().getOrNull()).isEqualTo(SUGGESTION)
    expectThat(second.await().getOrNull()).isEqualTo(SUGGESTION)
    coVerify(exactly = 1) { repository.getSuggestionBasedOn(any()) }
  }

  @Test
  fun `given a valid cached suggestion, when requested, then repository is not called`() = runTest {

    stubSettingsAndPrompt()
    cached = cachedSuggestion(fetchedAt = System.currentTimeMillis())

    val result = suggestionFor().first()

    expectThat(result.getOrNull()).isEqualTo(SUGGESTION)
    coVerify(exactly = 0) { repository.getSuggestionBasedOn(any()) }
  }

  private fun stubSettingsAndPrompt() {
    every { observeUserSettings() } returns flowOf(Result.success(userSettings()))
    every {
      buildWeatherSuggestionPrompt(any())
    } returns PROMPT
  }

  private fun suggestionFor() =
    generateWeatherSuggestion(
      todayDispositionEntries = NO_ENTRIES,
      weatherData = WEATHER,
      weatherKey = DEFAULT_WEATHER_KEY
    )

  private companion object {
    const val PROMPT = "weather prompt"
    val NO_ENTRIES = emptyList<UserDispositionEntry>()
  }
}
