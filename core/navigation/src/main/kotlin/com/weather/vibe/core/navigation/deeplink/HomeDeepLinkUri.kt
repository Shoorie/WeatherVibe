package com.weather.vibe.core.navigation.deeplink

import android.net.Uri

internal object HomeDeepLinkUri {

  const val HOST = "home"
  const val LOCATION_ID = "locationId"

  fun build(locationId: Long?): Uri =
    DeepLinkUris.build(HOST) {
      locationId?.let { appendQueryParameter(LOCATION_ID, it.toString()) }
    }
}
