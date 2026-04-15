package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER_KEY
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import com.weather.vibe.testing.widget.fixture.FakeWidgetSnapshotRepository
import com.weather.vibe.testing.widget.fixture.WidgetSnapshotFixtures.FETCHED_AT_EPOCH_MILLIS
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
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import java.io.IOException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class RefreshWidgetSnapshotTest {

  private val generateWeatherSuggestion = mockk<GenerateWeatherSuggestion>()
  private val getCurrentWeatherKey = mockk<GetCurrentWeatherKey>()
  private val getWeather = mockk<GetWeather>()
  private val snapshotRepository = FakeWidgetSnapshotRepository()
  private val timeProvider = mockk<TimeProvider>()
  private val refresh = RefreshWidgetSnapshot(
    generateWeatherSuggestion = generateWeatherSuggestion,
    getCurrentWeatherKey = getCurrentWeatherKey,
    getWeather = getWeather,
    snapshotRepository = snapshotRepository,
    timeProvider = timeProvider
  )

  @Before
  fun setUp() {
    every { getWeather(any()) } returns flowOf(success(WEATHER))
    every { getCurrentWeatherKey(WEATHER) } returns WEATHER_KEY
    every { generateWeatherSuggestion(WEATHER, WEATHER_KEY) } returns flowOf(success(SUGGESTION))
    every { timeProvider.nowEpochMillis() } returns FETCHED_AT_EPOCH_MILLIS
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when refreshed, then snapshot saved to repository`() = runTest {

    refresh(WARSAW)

    expectThat(snapshotRepository.savedSnapshots).hasSize(1)
  }

  @Test
  fun `when refreshed, then saved snapshot carries requested location`() = runTest {

    refresh(WARSAW)

    val saved = snapshotRepository.savedSnapshots.single()
    expectThat(saved.location).isEqualTo(WARSAW)
  }

  @Test
  fun `when refreshed, then saved snapshot carries generated suggestion`() = runTest {

    refresh(WARSAW)

    val saved = snapshotRepository.savedSnapshots.single()
    expectThat(saved.suggestion).isEqualTo(SUGGESTION)
  }

  @Test
  fun `when refreshed, then saved snapshot stamped with current time`() = runTest {

    refresh(WARSAW)

    val saved = snapshotRepository.savedSnapshots.single()
    expectThat(saved.fetchedAtEpochMillis).isEqualTo(FETCHED_AT_EPOCH_MILLIS)
  }

  @Test
  fun `given weather fetch fails, when refreshed, then rethrows cause`() = runTest {

    every { getWeather(any()) } returns flowOf(failure(IOException("offline")))

    expectThrows<IOException> { refresh(WARSAW) }
  }

  @Test
  fun `given weather fetch fails, when refreshed, then nothing saved`() = runTest {

    every { getWeather(any()) } returns flowOf(failure(IOException("offline")))

    runCatching { refresh(WARSAW) }

    expectThat(snapshotRepository.savedSnapshots).isEmpty()
  }

  @Test
  fun `given suggestion generation fails, when refreshed, then rethrows cause`() = runTest {

    every { generateWeatherSuggestion(WEATHER, WEATHER_KEY) } returns
      flowOf(failure(IllegalStateException("ai down")))

    expectThrows<IllegalStateException> { refresh(WARSAW) }
  }
}
