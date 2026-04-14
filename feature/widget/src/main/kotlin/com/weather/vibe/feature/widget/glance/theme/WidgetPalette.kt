package com.weather.vibe.feature.widget.glance.theme

import androidx.glance.color.ColorProvider
import com.weather.vibe.core.designsystem.theme.weatherDarkColors
import com.weather.vibe.core.designsystem.theme.weatherLightColors

internal object WidgetPalette {

  private val light = weatherLightColors()
  private val dark = weatherDarkColors()

  val background = ColorProvider(
    day = light.sheetSurface,
    night = dark.sheetSurface
  )

  val onBackground = ColorProvider(
    day = light.onBackground,
    night = dark.onBackground
  )

  val onBackgroundMuted = ColorProvider(
    day = light.onSurfaceVariant,
    night = dark.onSurfaceVariant
  )

  val accent = ColorProvider(
    day = light.accent,
    night = dark.accent
  )
}
