package com.weather.vibe.feature.widget.ui

import android.content.Context
import android.text.format.DateFormat
import com.weather.vibe.feature.widget.R
import org.koin.core.annotation.Factory
import java.util.Date

@Factory(binds = [WidgetResources::class])
internal class DefaultWidgetResources(
  private val context: Context
) : WidgetResources {

  override fun noLocationTitle(): String =
    context.getString(R.string.widget_no_location_title)

  override fun noLocationBody(): String =
    context.getString(R.string.widget_no_location_body)

  override fun waitingTitle(): String =
    context.getString(R.string.widget_waiting_title)

  override fun waitingBody(locationName: String): String =
    context.getString(R.string.widget_waiting_body, locationName)

  override fun errorTitle(): String =
    context.getString(R.string.widget_error_title)

  override fun errorBody(): String =
    context.getString(R.string.widget_error_body)

  override fun temperature(degrees: Int): String =
    context.getString(R.string.widget_temperature_format, degrees)

  override fun tapContentDescription(): String =
    context.getString(R.string.widget_tap_content_description)

  override fun weatherContentDescription(locationName: String, mood: String): String =
    context.getString(R.string.widget_weather_content_description, locationName, mood)

  override fun fetchTimestamp(epochMillis: Long): String =
    DateFormat.getTimeFormat(context).format(Date(epochMillis))
}
