package com.weather.vibe.feature.widget.ui

import android.content.Context
import com.weather.vibe.feature.widget.R
import org.koin.core.annotation.Factory

@Factory(binds = [WidgetResources::class])
internal class DefaultWidgetResources(
  private val context: Context
) : WidgetResources {

  override fun placeholderTitle(): String =
    context.getString(R.string.widget_placeholder_title)

  override fun placeholderBody(): String =
    context.getString(R.string.widget_placeholder_body)

  override fun waitingTitle(): String =
    context.getString(R.string.widget_waiting_title)

  override fun waitingBody(locationName: String): String =
    context.getString(R.string.widget_waiting_body, locationName)

  override fun temperature(degrees: Int): String =
    context.getString(R.string.widget_temperature_format, degrees)

  override fun tapContentDescription(): String =
    context.getString(R.string.widget_tap_content_description)

  override fun weatherContentDescription(locationName: String, mood: String): String =
    context.getString(R.string.widget_weather_content_description, locationName, mood)
}
