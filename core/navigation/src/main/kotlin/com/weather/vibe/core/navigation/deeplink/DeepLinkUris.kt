package com.weather.vibe.core.navigation.deeplink

import android.net.Uri

internal object DeepLinkUris {

  const val SCHEME = "weathervibe"

  inline fun build(host: String, block: Uri.Builder.() -> Unit = {}): Uri =
    Uri.Builder()
      .scheme(SCHEME)
      .authority(host)
      .apply(block)
      .build()
}
