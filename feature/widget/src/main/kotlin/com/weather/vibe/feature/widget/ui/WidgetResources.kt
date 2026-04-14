package com.weather.vibe.feature.widget.ui

interface WidgetResources {
  fun noLocationTitle(): String
  fun noLocationBody(): String
  fun waitingTitle(): String
  fun waitingBody(locationName: String): String
  fun temperature(degrees: Int): String
  fun tapContentDescription(): String
  fun weatherContentDescription(locationName: String, mood: String): String
}
