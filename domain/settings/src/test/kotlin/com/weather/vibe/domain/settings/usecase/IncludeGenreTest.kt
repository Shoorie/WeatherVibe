package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.testing.settings.fixture.FakeSettingsCache
import com.weather.vibe.testing.settings.fixture.GenreFixtures.JAZZ
import com.weather.vibe.testing.settings.fixture.GenreFixtures.METAL
import com.weather.vibe.testing.settings.fixture.GenreFixtures.POP
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEmpty

class IncludeGenreTest {

  @Test
  fun `given genre excluded, when invoked, then cache holds empty set`() = runTest {

    val after = includeFrom(userSettings(excludedGenres = setOf(POP)), POP)

    expectThat(after.excludedGenres).isEmpty()
  }

  @Test
  fun `given one of many genres matches, when invoked, then cache keeps the rest`() = runTest {

    val initial = userSettings(excludedGenres = setOf(POP, METAL, JAZZ))

    val after = includeFrom(initial, METAL)

    expectThat(after.excludedGenres).containsExactlyInAnyOrder(POP, JAZZ)
  }

  @Test
  fun `given genre not excluded, when invoked, then cache set is unchanged`() = runTest {

    val after = includeFrom(userSettings(excludedGenres = setOf(POP)), METAL)

    expectThat(after.excludedGenres).containsExactlyInAnyOrder(POP)
  }

  private suspend fun includeFrom(initial: UserSettings, genre: String): UserSettings {
    val cache = FakeSettingsCache(initial = initial)
    IncludeGenre(cache = cache).invoke(genre)
    return cache.current
  }
}
