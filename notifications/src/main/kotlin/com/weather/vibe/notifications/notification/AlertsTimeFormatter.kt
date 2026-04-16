package com.weather.vibe.notifications.notification

import android.content.Context
import com.weather.vibe.notifications.R
import org.koin.core.annotation.Factory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Factory
internal class AlertsTimeFormatter(private val context: Context) {

  private val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern(context.getString(R.string.alerts_time_format))

  fun format(time: LocalDateTime): String =
    time.format(formatter)
}
