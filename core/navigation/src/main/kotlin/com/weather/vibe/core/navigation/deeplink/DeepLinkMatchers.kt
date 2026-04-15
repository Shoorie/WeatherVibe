package com.weather.vibe.core.navigation.deeplink

internal object DeepLinkMatchers {

  val ALL: List<DeepLinkMatcher<DeepLink>> = listOf(
    HomeDeepLinkMatcher
  )
}
