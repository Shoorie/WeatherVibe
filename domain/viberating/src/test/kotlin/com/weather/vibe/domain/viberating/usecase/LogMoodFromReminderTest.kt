package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.viberating.fixture.WeatherSnapshotFixtures.SUNNY_20C
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class LogMoodFromReminderTest {

  private val captureWeatherSnapshot = mockk<CaptureWeatherSnapshot>()
  private val saveRatingEntry = mockk<SaveRatingEntry>()
  private val timeProvider = FakeTimeProvider()
  private val logMoodFromReminder = LogMoodFromReminder(
    captureWeatherSnapshot = captureWeatherSnapshot,
    saveRatingEntry = saveRatingEntry,
    timeProvider = timeProvider
  )

  @Before
  fun setUp() {
    coEvery { captureWeatherSnapshot() } returns SUNNY_20C
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when mood logged from reminder, then entry uses captured weather snapshot`() = runTest {

    val captured = captureSavedEntry()

    logMoodFromReminder(rating = 4)

    expectThat(captured().weather).isEqualTo(SUNNY_20C)
  }

  @Test
  fun `given capture returns unknown, when logged, then entry uses unknown weather`() = runTest {

    coEvery { captureWeatherSnapshot() } returns WeatherSnapshot.Unknown
    val captured = captureSavedEntry()

    logMoodFromReminder(rating = 4)

    expectThat(captured().weather).isEqualTo(WeatherSnapshot.Unknown)
  }

  @Test
  fun `when mood logged from reminder, then entry carries provided rating`() = runTest {

    val captured = captureSavedEntry()

    logMoodFromReminder(rating = 3)

    expectThat(captured().rating).isEqualTo(3)
  }

  @Test
  fun `when rating below minimum, then entry rating clamped to one`() = runTest {

    val captured = captureSavedEntry()

    logMoodFromReminder(rating = 0)

    expectThat(captured().rating).isEqualTo(1)
  }

  @Test
  fun `when rating above maximum, then entry rating clamped to five`() = runTest {

    val captured = captureSavedEntry()

    logMoodFromReminder(rating = 9)

    expectThat(captured().rating).isEqualTo(5)
  }

  @Test
  fun `when mood logged, then entry persisted once`() = runTest {

    coJustRun { saveRatingEntry(any()) }

    logMoodFromReminder(rating = 5)

    coVerify(exactly = 1) { saveRatingEntry(any()) }
  }

  private fun captureSavedEntry(): () -> RatingEntry {
    val slot = slot<RatingEntry>()
    coJustRun { saveRatingEntry(capture(slot)) }
    return { slot.captured }
  }
}
