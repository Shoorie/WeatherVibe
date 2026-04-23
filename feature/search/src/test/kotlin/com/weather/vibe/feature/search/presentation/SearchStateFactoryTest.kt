package com.weather.vibe.feature.search.presentation

import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.testing.location.fixture.LocationFixtures.WARSAW
import com.weather.vibe.testing.location.fixture.LocationFixtures.location
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class SearchStateFactoryTest {

  private val factory = SearchStateFactory(subtitle = LocationSubtitleFormatter())

  @Test
  fun `given no locations, when recents built, then state is idle`() {

    val result = factory.recentsStateOrIdle(
      query = QUERY,
      locations = emptyList(),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Idle>()
  }

  @Test
  fun `given favorite location, when recents built, then matching item is favorite`() {

    val result = factory.recentsStateOrIdle(
      query = QUERY,
      locations = listOf(WARSAW),
      favoriteLocationIds = setOf(WARSAW.id)
    )

    expectThat(result).isA<Recents>()
      .get { locations.single().isFavorite }.isTrue()
  }

  @Test
  fun `given location absent from favorites, when recents built, then item is not favorite`() {

    val result = factory.recentsStateOrIdle(
      query = QUERY,
      locations = listOf(WARSAW),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Recents>()
      .get { locations.single().isFavorite }.isFalse()
  }

  @Test
  fun `given admin1 null, when recents built, then subtitle contains only country`() {

    val result = factory.recentsStateOrIdle(
      query = QUERY,
      locations = listOf(location(admin1 = null, country = "Poland")),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Recents>()
      .get { locations.single().subtitle }.isEqualTo("Poland")
  }

  @Test
  fun `given admin1 and country, when recents built, then subtitle has both separated by comma`() {

    val result = factory.recentsStateOrIdle(
      query = QUERY,
      locations = listOf(location(admin1 = "Mazowieckie", country = "Poland")),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Recents>()
      .get { locations.single().subtitle }.isEqualTo("Mazowieckie, Poland")
  }

  @Test
  fun `given empty country, when recents built, then subtitle contains only admin1`() {

    val result = factory.recentsStateOrIdle(
      query = QUERY,
      locations = listOf(location(admin1 = "Mazowieckie", country = "")),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Recents>()
      .get { locations.single().subtitle }.isEqualTo("Mazowieckie")
  }

  @Test
  fun `given no locations, when results built, then state is empty`() {

    val result = factory.resultsStateOrEmpty(
      query = QUERY,
      locations = emptyList(),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Empty>()
  }

  @Test
  fun `given matching location, when results built, then state is results`() {

    val result = factory.resultsStateOrEmpty(
      query = QUERY,
      locations = listOf(WARSAW),
      favoriteLocationIds = emptySet()
    )

    expectThat(result).isA<Results>()
  }

  private companion object {
    const val QUERY = "warsaw"
  }
}
