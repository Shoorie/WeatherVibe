package com.weather.vibe.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import org.koin.core.annotation.Single

@Single(binds = [AnalyticsLogger::class])
internal class FirebaseAnalyticsLogger(
  private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsLogger {

  override fun log(event: AnalyticsEvent) {
    firebaseAnalytics.logEvent(event.name) {
      event.params.forEach { (key, value) -> param(key, value) }
    }
  }

  override fun logScreenView(screenName: String) {
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
    }
  }

  override fun setUserProperty(property: AnalyticsUserProperty) {
    firebaseAnalytics.setUserProperty(property.name, property.value)
  }
}
