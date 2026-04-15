package com.weather.vibe.core.navigation.deeplink

import android.net.Uri
import com.weather.vibe.core.navigation.deeplink.DeepLinkMatchers.ALL
import com.weather.vibe.core.navigation.deeplink.HomeDeepLinkMatcher.HOME_HOST
import com.weather.vibe.core.navigation.deeplink.HomeDeepLinkMatcher.LOCATION_ID_PARAM

sealed interface DeepLink {

  val uri: Uri

  data class Home(val locationId: Long?) : DeepLink {

    override val uri: Uri
      get() = DeepLinkUris.build(HOME_HOST) {
        if (locationId != null) {
          appendQueryParameter(LOCATION_ID_PARAM, locationId.toString())
        }
      }
  }

  companion object {

    fun parse(uri: Uri?): DeepLink? =
      uri?.let { candidate ->
        ALL.firstNotNullOfOrNull { it.match(candidate) }
      }
  }
}
