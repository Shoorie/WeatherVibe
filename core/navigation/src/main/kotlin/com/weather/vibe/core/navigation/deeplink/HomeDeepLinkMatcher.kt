package com.weather.vibe.core.navigation.deeplink

import android.net.Uri
import com.weather.vibe.core.navigation.deeplink.DeepLink.Home
import com.weather.vibe.core.navigation.deeplink.DeepLinkUris.SCHEME

internal object HomeDeepLinkMatcher : DeepLinkMatcher<Home> {

  private const val HOST = "home"
  private const val LOCATION_ID = "locationId"

  override fun match(uri: Uri): Home? {

    if (uri.scheme != SCHEME || uri.host != HOST) return null

    return Home(locationId = uri.getQueryParameter(LOCATION_ID)?.toLongOrNull())
  }

  fun build(locationId: Long?): Uri =
    DeepLinkUris.build(HOST) {
      locationId?.let { appendQueryParameter(LOCATION_ID, it.toString()) }
    }
}
