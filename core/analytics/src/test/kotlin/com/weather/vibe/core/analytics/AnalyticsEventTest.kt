package com.weather.vibe.core.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent.MoodLogged
import com.weather.vibe.core.analytics.AnalyticsEvent.NotificationShown
import com.weather.vibe.core.analytics.AnalyticsEvent.PlaylistOpened
import com.weather.vibe.core.analytics.PlaylistProvider.SPOTIFY
import com.weather.vibe.core.analytics.PlaylistProvider.YOUTUBE_MUSIC
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AnalyticsEventTest {

  @Test
  fun `when playlist opened in spotify, then provider param is spotify`() {

    val event = PlaylistOpened(provider = SPOTIFY)

    expectThat(event.name).isEqualTo("playlist_opened")
    expectThat(event.params).isEqualTo(mapOf("provider" to "spotify"))
  }

  @Test
  fun `when playlist opened in youtube music, then provider param is youtube music`() {

    val event = PlaylistOpened(provider = YOUTUBE_MUSIC)

    expectThat(event.params).isEqualTo(mapOf("provider" to "youtube_music"))
  }

  @Test
  fun `when mood logged, then no rating value is sent`() {

    expectThat(MoodLogged.name).isEqualTo("mood_logged")
    expectThat(MoodLogged.params).isEqualTo(emptyMap())
  }

  @Test
  fun `when notification shown, then kind param carries channel kind`() {

    val event = NotificationShown(kind = "MOOD_REMINDER")

    expectThat(event.name).isEqualTo("notification_shown")
    expectThat(event.params).isEqualTo(mapOf("kind" to "MOOD_REMINDER"))
  }
}
