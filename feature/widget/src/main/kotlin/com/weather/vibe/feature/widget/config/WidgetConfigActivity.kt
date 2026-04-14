package com.weather.vibe.feature.widget.config

import android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID
import android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

class WidgetConfigActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setResult(RESULT_CANCELED)

    val appWidgetId = extractAppWidgetId()
    if (appWidgetId == INVALID_APPWIDGET_ID) {
      finish()
      return
    }

    setContent {
      WeatherVibeTheme {
        WidgetConfigScreen(
          appWidgetId = appWidgetId,
          onCancel = ::finish,
          onFinish = ::completeWith
        )
      }
    }
  }

  private fun extractAppWidgetId(): Int =
    intent?.extras?.getInt(
      EXTRA_APPWIDGET_ID,
      INVALID_APPWIDGET_ID
    ) ?: INVALID_APPWIDGET_ID

  private fun completeWith(appWidgetId: Int) {
    val resultIntent = Intent().putExtra(EXTRA_APPWIDGET_ID, appWidgetId)
    setResult(RESULT_OK, resultIntent)
    finish()
  }
}
