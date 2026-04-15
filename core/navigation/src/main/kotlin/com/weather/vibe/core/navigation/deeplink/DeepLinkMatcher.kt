package com.weather.vibe.core.navigation.deeplink

import android.net.Uri

internal interface DeepLinkMatcher<out T : DeepLink> {
  fun match(uri: Uri): T?
}
