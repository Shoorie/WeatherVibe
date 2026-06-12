package com.weather.vibe.feature.home.analytics

import com.weather.vibe.feature.home.analytics.PlaylistProvider.SPOTIFY
import com.weather.vibe.feature.home.analytics.PlaylistProvider.YOUTUBE_MUSIC
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PlaylistOpenedEventTest {

  @Test
  fun `when spotify playlist opened, then provider param is spotify`() {

    val event = PlaylistOpenedEvent(provider = SPOTIFY)

    expectThat(event.name).isEqualTo("playlist_opened")
    expectThat(event.params).isEqualTo(mapOf("provider" to "spotify"))
  }

  @Test
  fun `when youtube music playlist opened, then provider param is youtube music`() {

    val event = PlaylistOpenedEvent(provider = YOUTUBE_MUSIC)

    expectThat(event.params).isEqualTo(mapOf("provider" to "youtube_music"))
  }
}
