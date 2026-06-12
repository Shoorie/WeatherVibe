package com.weather.vibe.feature.profile.analytics

import com.weather.vibe.core.analytics.AnalyticsUserProperty

internal data class HasUsernameProperty(
  private val hasUsername: Boolean
) : AnalyticsUserProperty {

  override val name: String = "has_username"

  override val value: String = hasUsername.toString()
}
