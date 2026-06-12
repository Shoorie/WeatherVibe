package com.weather.vibe.core.analytics

interface AnalyticsLogger {

  fun log(event: AnalyticsEvent)

  fun logScreenView(screenName: String)

  fun setUserProperty(property: AnalyticsUserProperty)
}
