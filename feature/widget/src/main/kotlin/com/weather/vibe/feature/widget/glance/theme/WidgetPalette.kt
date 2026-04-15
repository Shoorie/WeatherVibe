package com.weather.vibe.feature.widget.glance.theme

import androidx.compose.ui.graphics.Color
import androidx.glance.color.ColorProvider
import androidx.glance.unit.ColorProvider as GlanceColorProvider
import com.weather.vibe.core.designsystem.theme.weatherDarkColors
import com.weather.vibe.core.designsystem.theme.weatherLightColors

internal object WidgetPalette {

  private val light = weatherLightColors()
  private val dark = weatherDarkColors()

  private const val MUTED_ALPHA = 0.8f
  private const val SUBTLE_ALPHA = 0.6f

  val background: GlanceColorProvider =
    ColorProvider(day = light.accent, night = dark.accent)

  val onBackground: GlanceColorProvider =
    ColorProvider(day = Color.White, night = Color.White)

  val onBackgroundMuted: GlanceColorProvider =
    ColorProvider(
      day = Color.White.copy(alpha = MUTED_ALPHA),
      night = Color.White.copy(alpha = MUTED_ALPHA)
    )

  val onBackgroundSubtle: GlanceColorProvider =
    ColorProvider(
      day = Color.White.copy(alpha = SUBTLE_ALPHA),
      night = Color.White.copy(alpha = SUBTLE_ALPHA)
    )
}
