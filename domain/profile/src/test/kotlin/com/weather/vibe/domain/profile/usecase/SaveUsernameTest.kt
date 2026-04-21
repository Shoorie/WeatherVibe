package com.weather.vibe.domain.profile.usecase

import com.weather.vibe.domain.profile.cache.ProfileCache
import com.weather.vibe.domain.profile.usecase.SaveUsername.Companion.MAX_LENGTH
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

class SaveUsernameTest {

  private val cache = mockk<ProfileCache>()
  private val saveUsername = SaveUsername(cache = cache)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `given short name, when saved, then cache receives raw value`() = runTest {

    coJustRun { cache.saveUsername(any()) }

    saveUsername(username = SHORT_NAME)

    coVerify { cache.saveUsername(username = SHORT_NAME) }
  }

  @Test
  fun `given name equal to max length, when saved, then cache receives full value`() = runTest {

    val exactlyMax = "j".repeat(MAX_LENGTH)
    coJustRun { cache.saveUsername(any()) }

    saveUsername(username = exactlyMax)

    coVerify { cache.saveUsername(username = exactlyMax) }
  }

  @Test
  fun `given name over max length, when saved, then cache receives truncated value`() = runTest {

    val overMax = "j".repeat(MAX_LENGTH + OVERFLOW)
    val truncated = "j".repeat(MAX_LENGTH)
    coJustRun { cache.saveUsername(any()) }

    saveUsername(username = overMax)

    coVerify { cache.saveUsername(username = truncated) }
  }

  private companion object {
    const val SHORT_NAME = "John"
    const val OVERFLOW = 10
  }
}
