package com.weather.vibe.feature.home.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent

internal data class PlaylistOpenedEvent(
  private val provider: PlaylistProvider
) : AnalyticsEvent {

  override val name: String = "playlist_opened"

  override val params: Map<String, String> =
    mapOf("provider" to provider.analyticsValue)
}
