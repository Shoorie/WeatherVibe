package com.weather.vibe.core.analytics

interface AnalyticsLogger {
  fun log(event: AnalyticsEvent)
}
