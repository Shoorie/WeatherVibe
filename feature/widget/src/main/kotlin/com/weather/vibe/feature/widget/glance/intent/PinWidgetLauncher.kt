package com.weather.vibe.feature.widget.glance.intent

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import com.weather.vibe.feature.widget.glance.WeatherVibeWidgetReceiver
import org.koin.core.annotation.Single

@Single
class PinWidgetLauncher {

  fun isSupported(context: Context): Boolean =
    AppWidgetManager.getInstance(context).isRequestPinAppWidgetSupported

  fun isAlreadyPinned(context: Context): Boolean =
    AppWidgetManager.getInstance(context)
      .getAppWidgetIds(provider(context))
      .isNotEmpty()

  fun pin(activity: Activity) {
    if (!isSupported(activity)) return
    AppWidgetManager.getInstance(activity).requestPinAppWidget(
      provider(activity),
      null,
      null
    )
  }

  private fun provider(context: Context): ComponentName =
    ComponentName(context, WeatherVibeWidgetReceiver::class.java)
}
