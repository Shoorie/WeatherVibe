package com.weather.vibe.feature.widget.config.ui

import android.content.Context
import com.weather.vibe.feature.widget.R
import org.koin.core.annotation.Factory

@Factory(binds = [WidgetConfigResources::class])
internal class DefaultWidgetConfigResources(private val context: Context) : WidgetConfigResources {

  override fun defaultError(): String =
    context.getString(R.string.widget_config_default_error)

  override fun emptyHint(): String =
    context.getString(R.string.widget_config_empty_hint)

  override fun formatSubtitle(admin1: String?, country: String): String = when {
    admin1.isNullOrBlank() -> country
    else -> context.getString(R.string.widget_config_subtitle_format, admin1, country)
  }
}
