package com.weather.vibe.feature.widget.glance.theme

import androidx.compose.ui.unit.sp
import androidx.glance.text.FontWeight
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette.onBackground
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette.onBackgroundMuted
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette.onBackgroundSubtle

internal object WidgetTextStyles {

  val hero: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 52.sp,
    textAlign = TextAlign.Center
  )

  val heroSmall: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 28.sp,
    textAlign = TextAlign.Center
  )

  val conditionLabel: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium,
    textAlign = TextAlign.Center
  )

  val location: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold
  )

  val timestamp: TextStyle = TextStyle(
    color = onBackgroundSubtle,
    fontSize = 11.sp
  )

  val mood: TextStyle = TextStyle(
    color = onBackgroundSubtle,
    fontSize = 11.sp
  )

  val temperature: TextStyle = TextStyle(
    color = onBackgroundMuted,
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium
  )

  val title: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium
  )

  val body: TextStyle = TextStyle(
    color = onBackgroundMuted,
    fontSize = 11.sp
  )
}
