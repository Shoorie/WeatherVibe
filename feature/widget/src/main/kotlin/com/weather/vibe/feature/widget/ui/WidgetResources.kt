package com.weather.vibe.feature.widget.ui

interface WidgetResources {
  fun placeholderTitle(): String
  fun placeholderBody(): String
  fun waitingTitle(): String
  fun waitingBody(locationName: String): String
  fun temperature(degrees: Int): String
  fun tapContentDescription(): String
  fun weatherContentDescription(locationName: String, mood: String): String
}
