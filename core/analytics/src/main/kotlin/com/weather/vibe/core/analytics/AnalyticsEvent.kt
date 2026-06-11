package com.weather.vibe.core.analytics

sealed interface AnalyticsEvent {

  val name: String
  val params: Map<String, String>

  data class PlaylistOpened(val provider: PlaylistProvider) : AnalyticsEvent {

    override val name: String =
      "playlist_opened"

    override val params: Map<String, String> =
      mapOf("provider" to provider.analyticsValue)
  }

  data object MoodLogged : AnalyticsEvent {

    override val name: String =
      "mood_logged"

    override val params: Map<String, String> =
      emptyMap()
  }

  data class NotificationShown(val kind: String) : AnalyticsEvent {

    override val name: String =
      "notification_shown"

    override val params: Map<String, String> =
      mapOf("kind" to kind)
  }
}
