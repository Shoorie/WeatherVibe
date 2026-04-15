package com.weather.vibe.core.navigation.deeplink

import android.net.Uri
import com.weather.vibe.core.navigation.deeplink.DeepLink.Home
import com.weather.vibe.core.navigation.deeplink.DeepLinkUris.SCHEME
import com.weather.vibe.core.navigation.deeplink.HomeDeepLinkUri.HOST
import com.weather.vibe.core.navigation.deeplink.HomeDeepLinkUri.LOCATION_ID

internal object HomeDeepLinkMatcher : DeepLinkMatcher<Home> {

  override fun match(uri: Uri): Home? {

    if (uri.scheme != SCHEME || uri.host != HOST) return null

    val locationId = uri
      .getQueryParameter(LOCATION_ID)
      ?.toLongOrNull()
    return Home(locationId)
  }
}
