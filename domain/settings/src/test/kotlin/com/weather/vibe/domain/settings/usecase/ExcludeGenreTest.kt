package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.testing.settings.fixture.FakeSettingsCache
import com.weather.vibe.testing.settings.fixture.GenreFixtures.METAL
import com.weather.vibe.testing.settings.fixture.GenreFixtures.POP
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.userSettings
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder

class ExcludeGenreTest {

  @Test
  fun `given no excluded genres, when invoked, then cache holds the added genre`() = runTest {

    val after = excludeFrom(userSettings(excludedGenres = emptySet()), POP)

    expectThat(after.excludedGenres).containsExactlyInAnyOrder(POP)
  }

  @Test
  fun `given existing genres, when invoked, then cache appends the new genre`() = runTest {

    val after = excludeFrom(userSettings(excludedGenres = setOf(POP)), METAL)

    expectThat(after.excludedGenres).containsExactlyInAnyOrder(POP, METAL)
  }

  @Test
  fun `given genre already excluded, when invoked, then cache set is unchanged`() = runTest {

    val after = excludeFrom(userSettings(excludedGenres = setOf(POP)), POP)

    expectThat(after.excludedGenres).containsExactlyInAnyOrder(POP)
  }

  private suspend fun excludeFrom(initial: UserSettings, genre: String): UserSettings {
    val cache = FakeSettingsCache(initial = initial)
    ExcludeGenre(cache = cache).invoke(genre)
    return cache.current
  }
}
