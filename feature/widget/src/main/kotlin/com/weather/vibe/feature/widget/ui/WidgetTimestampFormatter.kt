package com.weather.vibe.feature.widget.ui

import android.content.Context
import android.text.format.DateFormat
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
internal class WidgetTimestampFormatter(private val context: Context) {

  operator fun invoke(epochMillis: Long): String =
    DateFormat.getTimeFormat(context).format(Date(epochMillis))
}
