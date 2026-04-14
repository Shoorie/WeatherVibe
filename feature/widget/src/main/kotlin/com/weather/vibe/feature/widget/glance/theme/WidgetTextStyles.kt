package com.weather.vibe.feature.widget.glance.theme

import androidx.compose.ui.unit.sp
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette.accent
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette.onBackground
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette.onBackgroundMuted

internal object WidgetTextStyles {

  val heroEmoji: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 36.sp
  )

  val temperature: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
  )

  val title: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium
  )

  val body: TextStyle = TextStyle(
    color = onBackground,
    fontSize = 13.sp
  )

  val caption: TextStyle = TextStyle(
    color = onBackgroundMuted,
    fontSize = 11.sp
  )

  val moodAccent: TextStyle = TextStyle(
    color = accent,
    fontSize = 11.sp,
    fontWeight = FontWeight.Bold
  )
}
