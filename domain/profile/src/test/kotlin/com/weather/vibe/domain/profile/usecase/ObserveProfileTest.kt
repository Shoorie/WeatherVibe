package com.weather.vibe.domain.profile.usecase

import app.cash.turbine.test
import com.weather.vibe.domain.profile.cache.ProfileCache
import com.weather.vibe.domain.profile.model.Profile
import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ObserveProfileTest {

  private val cache = mockk<ProfileCache>()
  private val time = FakeTimeProvider()
  private val observeProfile = ObserveProfile(cache = cache, time = time)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when cache emits profile with known install time, then summary carries username`() = runTest {

    every { cache.observeProfile() } returns flowOf(
      Profile(
        username = USERNAME,
        installedAtMillis = time.nowEpochMillis()
      )
    )

    observeProfile().test {
      expectThat(awaitItem().username).isEqualTo(USERNAME)
      awaitComplete()
    }
  }

  @Test
  fun `given install time matches now, when observed, then usage days is one`() = runTest {

    every { cache.observeProfile() } returns flowOf(
      Profile(
        username = USERNAME,
        installedAtMillis = time.nowEpochMillis()
      )
    )

    observeProfile().test {
      expectThat(awaitItem().usageDays).isEqualTo(FIRST_DAY)
      awaitComplete()
    }
  }

  @Test
  fun `given install time two days ago, when observed, then usage days counts elapsed days`() = runTest {

    val twoDaysAgo = time.nowEpochMillis() - DAYS_TWO * MILLIS_PER_DAY
    every { cache.observeProfile() } returns flowOf(
      Profile(
        username = USERNAME,
        installedAtMillis = twoDaysAgo
      )
    )

    observeProfile().test {
      expectThat(awaitItem().usageDays).isEqualTo(DAYS_TWO.toInt() + FIRST_DAY)
      awaitComplete()
    }
  }

  @Test
  fun `given install time never set, when observed, then cache bootstraps now`() = runTest {

    every { cache.observeProfile() } returns flowOf(
      Profile(
        username = USERNAME,
        installedAtMillis = NEVER
      )
    )
    coJustRun { cache.saveInstalledAtMillis(any()) }

    observeProfile().test {
      awaitItem()
      awaitComplete()
    }

    coVerify { cache.saveInstalledAtMillis(time.nowEpochMillis()) }
  }

  @Test
  fun `given install time never set, when observed, then usage days is one`() = runTest {

    every { cache.observeProfile() } returns flowOf(
      Profile(
        username = USERNAME,
        installedAtMillis = NEVER
      )
    )
    coJustRun { cache.saveInstalledAtMillis(any()) }

    observeProfile().test {
      expectThat(awaitItem().usageDays).isEqualTo(FIRST_DAY)
      awaitComplete()
    }
  }

  @Test
  fun `given install time is set, when observed, then no bootstrap write`() = runTest {

    every { cache.observeProfile() } returns flowOf(
      Profile(
        username = USERNAME,
        installedAtMillis = time.nowEpochMillis()
      )
    )

    observeProfile().test {
      awaitItem()
      awaitComplete()
    }

    coVerify(exactly = 0) { cache.saveInstalledAtMillis(any()) }
  }

  private companion object {
    const val USERNAME = "John"
    const val NEVER = 0L
    const val FIRST_DAY = 1
    const val DAYS_TWO = 2L
    const val MILLIS_PER_DAY = 24L * 60 * 60 * 1000
  }
}
