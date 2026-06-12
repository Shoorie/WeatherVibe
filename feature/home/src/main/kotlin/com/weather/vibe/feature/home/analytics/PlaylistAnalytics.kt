package com.weather.vibe.feature.home.analytics

import com.weather.vibe.core.analytics.AnalyticsLogger
import org.koin.core.annotation.Single

@Single
internal class PlaylistAnalytics(
  private val logger: AnalyticsLogger
) {

  fun onPlaylistOpened(provider: PlaylistProvider) =
    logger.log(PlaylistOpenedEvent(provider = provider))
}
