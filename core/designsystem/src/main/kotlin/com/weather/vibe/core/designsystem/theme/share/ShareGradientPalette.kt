package com.weather.vibe.core.designsystem.theme.share

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.CLOUDY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.NIGHT
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.RAINY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.SNOWY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.STORMY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.SUNNY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.CloudyBottom
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.CloudyMiddle
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.CloudyTop
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.GlowCloudy
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.GlowNight
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.GlowRainy
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.GlowSnowy
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.GlowStormy
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.GlowSunny
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.NightBottom
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.NightMiddle
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.NightTop
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.RainyBottom
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.RainyMiddle
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.RainyTop
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.SnowyBottom
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.SnowyMiddle
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.SnowyTop
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.StormyBottom
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.StormyMiddle
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.StormyTop
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.SunnyBottom
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.SunnyMiddle
import com.weather.vibe.core.designsystem.theme.share.ShareGradientTokens.SunnyTop

@Immutable
data class ShareGradient(
  val background: Brush,
  val glow: Color,
  val onSurface: Color,
  val onSurfaceSoft: Color
)

object ShareGradientPalette {

  fun gradientFor(key: ShareGradientKey): ShareGradient = when (key) {
    SUNNY -> sunnyGradient
    CLOUDY -> cloudyGradient
    RAINY -> rainyGradient
    STORMY -> stormyGradient
    SNOWY -> snowyGradient
    NIGHT -> nightGradient
  }

  private val sunnyGradient = ShareGradient(
    background = verticalBrush(
      top = SunnyTop,
      middle = SunnyMiddle,
      bottom = SunnyBottom
    ),
    glow = GlowSunny,
    onSurface = Color.White,
    onSurfaceSoft = Color(0xE6FFFFFF)
  )

  private val cloudyGradient = ShareGradient(
    background = verticalBrush(
      top = CloudyTop,
      middle = CloudyMiddle,
      bottom = CloudyBottom
    ),
    glow = GlowCloudy,
    onSurface = Color.White,
    onSurfaceSoft = Color(0xD9FFFFFF)
  )

  private val rainyGradient = ShareGradient(
    background = verticalBrush(
      top = RainyTop,
      middle = RainyMiddle,
      bottom = RainyBottom
    ),
    glow = GlowRainy,
    onSurface = Color.White,
    onSurfaceSoft = Color(0xD9FFFFFF)
  )

  private val stormyGradient = ShareGradient(
    background = verticalBrush(
      top = StormyTop,
      middle = StormyMiddle,
      bottom = StormyBottom
    ),
    glow = GlowStormy,
    onSurface = Color.White,
    onSurfaceSoft = Color(0xD9FFFFFF)
  )

  private val snowyGradient = ShareGradient(
    background = verticalBrush(
      top = SnowyTop,
      middle = SnowyMiddle,
      bottom = SnowyBottom
    ),
    glow = GlowSnowy,
    onSurface = Color(0xFF0F172A),
    onSurfaceSoft = Color(0xCC0F172A)
  )

  private val nightGradient = ShareGradient(
    background = verticalBrush(
      top = NightTop,
      middle = NightMiddle,
      bottom = NightBottom
    ),
    glow = GlowNight,
    onSurface = Color.White,
    onSurfaceSoft = Color(0xD9FFFFFF)
  )

  private fun verticalBrush(top: Color, middle: Color, bottom: Color): Brush =
    Brush.linearGradient(
      colorStops = arrayOf(
        TOP_STOP to top,
        MIDDLE_STOP to middle,
        BOTTOM_STOP to bottom
      ),
      start = Offset.Zero,
      end = Offset.Infinite
    )

  private const val TOP_STOP = 0f
  private const val MIDDLE_STOP = 0.55f
  private const val BOTTOM_STOP = 1f
}
