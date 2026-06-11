package com.weather.vibe.feature.home.ui.screen.callbacks

import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.UriHandler
import com.weather.vibe.core.analytics.AnalyticsEvent.PlaylistOpened
import com.weather.vibe.core.analytics.AnalyticsLogger
import com.weather.vibe.core.analytics.PlaylistProvider.SPOTIFY
import com.weather.vibe.core.analytics.PlaylistProvider.YOUTUBE_MUSIC

@Stable
internal class MoodSheetCallbacks(
  private val analyticsLogger: AnalyticsLogger,
  private val uriHandler: UriHandler,
  setVisible: (Boolean) -> Unit
) {

  val onShow: () -> Unit = { setVisible(true) }
  val onDismiss: () -> Unit = { setVisible(false) }
  val onOpenSpotify: (String, String) -> Unit = { appUri, webUrl -> openSpotify(appUri, webUrl) }
  val onOpenYtMusic: (String) -> Unit = { url -> openYtMusic(url) }

  private fun openSpotify(appUri: String, webUrl: String) {
    analyticsLogger.log(PlaylistOpened(provider = SPOTIFY))
    try {
      uriHandler.openUri(appUri)
    } catch (_: IllegalArgumentException) {
      openUriSafely(webUrl)
    }
  }

  private fun openYtMusic(url: String) {
    analyticsLogger.log(PlaylistOpened(provider = YOUTUBE_MUSIC))
    openUriSafely(url)
  }

  private fun openUriSafely(uri: String) {
    try {
      uriHandler.openUri(uri)
    } catch (_: IllegalArgumentException) {
      // No-op.
    }
  }
}
