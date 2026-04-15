package com.weather.vibe.feature.widget.glance.theme

import androidx.compose.ui.graphics.Color
import androidx.glance.color.ColorProvider
import androidx.glance.unit.ColorProvider as GlanceColorProvider
import com.weather.vibe.core.designsystem.theme.weatherDarkColors
import com.weather.vibe.core.designsystem.theme.weatherLightColors

internal object WidgetPalette {

  private val light = weatherLightColors()
  private val dark = weatherDarkColors()

  val background: GlanceColorProvider =
    ColorProvider(day = light.accent, night = dark.accent)

  val onBackground: GlanceColorProvider =
    ColorProvider(day = Color.White, night = Color.White)

  val onBackgroundMuted: GlanceColorProvider =
    ColorProvider(day = Color(0xCCFFFFFF), night = Color(0xCCFFFFFF))

  val onBackgroundSubtle: GlanceColorProvider =
    ColorProvider(day = Color(0x99FFFFFF), night = Color(0x99FFFFFF))
}
