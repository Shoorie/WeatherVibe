package com.weather.vibe.feature.home.ui.screen.callbacks

import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.UriHandler

@Stable
internal class MoodSheetCallbacks(
  uriHandler: UriHandler,
  setVisible: (Boolean) -> Unit
) {
  val onShow: () -> Unit = { setVisible(true) }
  val onDismiss: () -> Unit = { setVisible(false) }
  val onOpenSpotify: (String) -> Unit = { query -> openUriSafely(uriHandler, query) }
  val onOpenYtMusic: (String) -> Unit = { url -> openUriSafely(uriHandler, url) }

  private fun openUriSafely(uriHandler: UriHandler, uri: String) {
    runCatching { uriHandler.openUri(uri) }
  }
}
