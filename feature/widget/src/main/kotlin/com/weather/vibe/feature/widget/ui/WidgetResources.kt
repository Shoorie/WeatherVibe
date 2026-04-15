package com.weather.vibe.feature.widget.ui

import android.content.Context
import android.text.format.DateFormat
import com.weather.vibe.feature.widget.R
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
internal class WidgetResources(private val context: Context) {

  fun noLocationTitle(): String =
    context.getString(R.string.widget_no_location_title)

  fun noLocationBody(): String =
    context.getString(R.string.widget_no_location_body)

  fun waitingTitle(): String =
    context.getString(R.string.widget_waiting_title)

  fun waitingBody(locationName: String): String =
    context.getString(R.string.widget_waiting_body, locationName)

  fun errorTitle(): String =
    context.getString(R.string.widget_error_title)

  fun errorBody(): String =
    context.getString(R.string.widget_error_body)

  fun temperature(degrees: Int): String =
    context.getString(R.string.widget_temperature_format, degrees)

  fun weatherContentDescription(locationName: String, mood: String): String =
    context.getString(R.string.widget_weather_content_description, locationName, mood)

  fun fetchTimestamp(epochMillis: Long): String =
    DateFormat.getTimeFormat(context).format(Date(epochMillis))
}
