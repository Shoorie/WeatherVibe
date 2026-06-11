package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SINGLE_GENRE
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.WHITESPACE_GENRES
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.suggestion
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BuildPlaylistQueryTest {

  private val buildPlaylistQuery = BuildPlaylistQuery()

  @Test
  fun `when suggestion has multiple genres, then spotify app uri joins them with spaces`() {

    val result = buildPlaylistQuery(SUGGESTION)

    expectThat(result.spotifyApp)
      .isEqualTo("spotify:search:Indie Pop Electronic Jazz")
  }

  @Test
  fun `when suggestion has multiple genres, then spotify web url encodes spaces`() {

    val result = buildPlaylistQuery(SUGGESTION)

    expectThat(result.spotifyWeb)
      .isEqualTo("https://open.spotify.com/search/Indie%20Pop%20Electronic%20Jazz")
  }

  @Test
  fun `when suggestion has multiple genres, then yt music query uses first genre only`() {

    val result = buildPlaylistQuery(SUGGESTION)

    expectThat(result.ytMusic)
      .isEqualTo("https://music.youtube.com/search?q=Indie Pop")
  }

  @Test
  fun `when suggestion has single genre, then spotify app uri contains only that genre`() {

    val result = buildPlaylistQuery(SINGLE_GENRE)

    expectThat(result.spotifyApp)
      .isEqualTo("spotify:search:Indie Pop")
  }

  @Test
  fun `given whitespace around genres, when built, then trim before joining`() {

    val result = buildPlaylistQuery(WHITESPACE_GENRES)

    expectThat(result.spotifyApp)
      .isEqualTo("spotify:search:Indie Pop Electronic Jazz")
  }

  @Test
  fun `given empty genre list, when built, then spotify app uri is only scheme`() {

    val result = buildPlaylistQuery(suggestion(genres = emptyList()))

    expectThat(result.spotifyApp)
      .isEqualTo("spotify:search:")
  }

  @Test
  fun `given empty genre list, when built, then spotify web url is only search url`() {

    val result = buildPlaylistQuery(suggestion(genres = emptyList()))

    expectThat(result.spotifyWeb)
      .isEqualTo("https://open.spotify.com/search/")
  }

  @Test
  fun `given empty genre list, when built, then yt music query is only base url`() {

    val result = buildPlaylistQuery(suggestion(genres = emptyList()))

    expectThat(result.ytMusic)
      .isEqualTo("https://music.youtube.com/search?q=")
  }
}
