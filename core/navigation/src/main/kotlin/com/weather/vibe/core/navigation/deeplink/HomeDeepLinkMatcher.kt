package com.weather.vibe.core.navigation.deeplink

import android.net.Uri
import com.weather.vibe.core.navigation.deeplink.DeepLink.Home
import com.weather.vibe.core.navigation.deeplink.DeepLinkUris.SCHEME

internal object HomeDeepLinkMatcher : DeepLinkMatcher<Home> {

  const val HOME_HOST = "home"
  const val LOCATION_ID_PARAM = "locationId"

  override fun match(uri: Uri): Home? {

    if (uri.scheme != SCHEME || uri.host != HOME_HOST) return null

    val locationId = uri
      .getQueryParameter(LOCATION_ID_PARAM)
      ?.toLongOrNull()

    return Home(locationId = locationId)
  }
}
