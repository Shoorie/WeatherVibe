package com.weather.vibe.core.navigation.deeplink

import android.net.Uri
import com.weather.vibe.core.navigation.deeplink.DeepLinkMatchers.ALL

sealed interface DeepLink {

  val uri: Uri

  data class Home(val locationId: Long?) : DeepLink {
    override val uri: Uri get() = HomeDeepLinkMatcher.build(locationId)
  }

  companion object {

    fun parse(uri: Uri?): DeepLink? =
      uri?.let { candidate ->
        ALL.firstNotNullOfOrNull { it.match(candidate) }
      }
  }
}
