package com.weather.vibe.core.analytics

interface AnalyticsEvent {

  val name: String
  val params: Map<String, String>
}
