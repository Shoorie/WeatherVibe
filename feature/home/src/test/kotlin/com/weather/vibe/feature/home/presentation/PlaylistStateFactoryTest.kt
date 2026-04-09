package com.weather.vibe.feature.home.presentation

import com.weather.vibe.feature.home.presentation.fixture.WeatherSuggestionFixtures.SUGGESTION
import com.weather.vibe.feature.home.presentation.fixture.WeatherSuggestionFixtures.SINGLE_GENRE
import com.weather.vibe.feature.home.presentation.fixture.WeatherSuggestionFixtures.WHITESPACE_GENRES
import com.weather.vibe.feature.home.presentation.fixture.WeatherSuggestionFixtures.suggestion
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.map

class PlaylistStateFactoryTest {

  private val factory = PlaylistStateFactory()

  @Test
  fun `when playlist created, then map genre names to chips`() {

    val result = factory.create(SUGGESTION)

    expectThat(result.genres).map { it.name }
      .containsExactly("Indie Pop", "Electronic", "Jazz")
  }

  @Test
  fun `when playlist created, then genre chips are not rejecting`() {
    val result = factory.create(SUGGESTION)

    expectThat(result.genres).map { it.isRejecting }
      .containsExactly(false, false, false)
  }

  @Test
  fun `when playlist created, then map mood`() {

    val result = factory.create(SUGGESTION)

    expectThat(result.mood).isEqualTo("Uplifting")
  }

  @Test
  fun `when playlist created, then map mood description`() {

    val result = factory.create(SUGGESTION)

    expectThat(result.moodDescription)
      .isEqualTo("Bright and energetic vibes")
  }

  @Test
  fun `when playlist created, then build spotify query from all genres`() {

    val result = factory.create(SUGGESTION)

    expectThat(result.spotifyQuery)
      .isEqualTo("spotify:search:Indie Pop Electronic Jazz")
  }

  @Test
  fun `when playlist created, then build youtube music url from first genre`() {

    val result = factory.create(SUGGESTION)

    expectThat(result.ytMusicUrl)
      .isEqualTo("https://music.youtube.com/search?q=Indie Pop")
  }

  @Test
  fun `given single genre, when playlist created, then spotify query contains one genre`() {

    val result = factory.create(SINGLE_GENRE)

    expectThat(result.spotifyQuery)
      .isEqualTo("spotify:search:Indie Pop")
  }

  @Test
  fun `given genres with whitespace, when playlist created, then trim genre names`() {

    val result = factory.create(WHITESPACE_GENRES)

    expectThat(result.genres).map { it.name }
      .containsExactly("Indie Pop", "Electronic", "Jazz")
  }

  @Test
  fun `given empty genres, when playlist created, then spotify query has no genres`() {

    val result = factory.create(suggestion(genres = emptyList()))

    expectThat(result.spotifyQuery)
      .isEqualTo("spotify:search:")
  }

  @Test
  fun `given empty genres, when playlist created, then youtube url has empty query`() {
    val result = factory.create(suggestion(genres = emptyList()))

    expectThat(result.ytMusicUrl)
      .isEqualTo("https://music.youtube.com/search?q=")
  }
}
