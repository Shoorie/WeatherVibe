package com.weather.vibe.feature.widget.ui.theme

import androidx.compose.ui.unit.sp
import androidx.glance.text.FontWeight
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.weather.vibe.feature.widget.ui.theme.WidgetPalette.onBackground
import com.weather.vibe.feature.widget.ui.theme.WidgetPalette.onBackgroundMuted
import com.weather.vibe.feature.widget.ui.theme.WidgetPalette.onBackgroundSubtle

internal object WidgetTextStyles {

  val conditionEmoji: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 52.sp,
    textAlign = TextAlign.Center
  )

  val conditionLabel: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium,
    textAlign = TextAlign.Center
  )

  val locationName: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold
  )

  val fetchedAtLabel: TextStyle = TextStyle(
    color = onBackgroundSubtle,
    fontSize = 11.sp
  )

  val mood: TextStyle = TextStyle(
    color = onBackgroundSubtle,
    fontSize = 11.sp,
    textAlign = TextAlign.Center
  )

  val temperature: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
  )

  val messageEmoji: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 28.sp,
    textAlign = TextAlign.Center
  )

  val messageTitle: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium
  )

  val messageBody: TextStyle = TextStyle(
    color = onBackgroundMuted,
    fontSize = 11.sp
  )
}
