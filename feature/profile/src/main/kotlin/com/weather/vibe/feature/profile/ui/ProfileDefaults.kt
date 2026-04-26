package com.weather.vibe.feature.profile.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object ProfileDefaults {

  val ListRowRadius = 22.dp
  val ListRowShape = RoundedCornerShape(ListRowRadius)

  val NavIconContainerSize = 44.dp
  val NavIconSize = 22.dp
  val NavIconCornerRadius = 14.dp
  val NavIconShape = RoundedCornerShape(NavIconCornerRadius)

  val HeroRadius = 28.dp
  val HeroShape = RoundedCornerShape(HeroRadius)
  val HeroPaddingTop = 18.dp
  val HeroPaddingHorizontal = 22.dp
  val HeroPaddingBottom = 18.dp
  val HeroAvatarSize = 56.dp
  val HeroAvatarBorderWidth = 2.dp
  const val HeroAvatarBorderAlpha = 0.55f
  val HeroAvatarInitialFontSize = 22.sp
  val HeroChipPaddingHorizontal = 12.dp
  val HeroChipPaddingVertical = 8.dp
  val HeroChipMarginTop = 14.dp
  const val HeroChipAlpha = 0.2f
  val HeroChipBackground: Color = Color.White.copy(alpha = HeroChipAlpha)
  val HeroGreetingToHandSpacing = 6.dp
  val HeroAvatarToTextsSpacing = 14.dp
  const val HeroSubtitleAlpha = 0.85f
  const val HeroMaxGreetingLines = 2
  val HeroDecorationCloudWidth = 90.dp
  val HeroDecorationCloudHeight = 62.dp
  val HeroDecorationCloudOffsetX = 10.dp
  val HeroDecorationCloudOffsetY = 0.dp
  val HeroDecorationCloudFloatRangeDp = 7f
  const val HeroDecorationCloudFloatDurationMs = 3000
  const val HeroDecorationCloudAlpha = 0.35f
  const val HeroDecorationCloudTransitionLabel = "FloatingCloudTransition"
  const val HeroDecorationCloudOffsetLabel = "FloatingCloudOffset"
  val HeroDecorationSunSize = 40.dp
  val HeroDecorationSunOffsetX = (-16).dp
  val HeroDecorationSunOffsetY = 50.dp
  const val HeroDecorationSunAlpha = 0.22f

  val WavingHandFontSize = 22.sp
  const val WavingHandDurationMs = 2600
  val WavingHandEasing = LinearEasing
  const val WavingHandEmoji = "👋"
  const val WavingHandPivot = 0.7f
  const val WavingHandTransitionLabel = "WavingHandTransition"
  const val WavingHandProgressLabel = "WavingHandProgress"
  val WavingHandKeyframes: List<Pair<Float, Float>> = listOf(
    0.0f to 0f,
    0.1f to 14f,
    0.2f to -8f,
    0.3f to 14f,
    0.4f to -4f,
    0.5f to 10f,
    0.6f to 0f,
    1.0f to 0f
  )

  val StatTilePaddingHorizontal = 8.dp
  val StatTilePaddingVertical = 12.dp
  val StatTileEmojiFontSize = 20.sp
  val StatTileGap = 4.dp

  val VibeRowRadius = 22.dp
  val VibeRowShape = RoundedCornerShape(VibeRowRadius)
  val VibeRowPaddingHorizontal = 18.dp
  val VibeRowPaddingVertical = 16.dp
  val VibeAvatarSize = 50.dp
  val VibeAvatarSmileySize = 36.dp
  val VibeRowAvatarToTextsSpacing = 14.dp
  val VibeRowAverageToStreakSpacing = 8.dp
  val VibeRowTitleToValueSpacing = 3.dp
  val VibeStarsDecorWidth = 100.dp
  val VibeStarsDecorHeight = 60.dp
  const val VibeStarsDecorAlpha = 0.55f

  val AppearanceSegmentRadius = 14.dp
  val AppearanceSegmentShape = RoundedCornerShape(AppearanceSegmentRadius)
  val AppearanceSegmentChipRadius = 10.dp
  val AppearanceSegmentChipShape = RoundedCornerShape(AppearanceSegmentChipRadius)
  val AppearanceSegmentInnerPadding = 4.dp
  val AppearanceSegmentChipPaddingHorizontal = 10.dp
  val AppearanceSegmentChipPaddingVertical = 8.dp
  val AppearanceSegmentTopSpacing = 12.dp
  val AppearanceSegmentMinHeight = 36.dp
  const val AppearanceSegmentBackgroundAlpha = 0.06f
  const val AppearanceSegmentTransitionMs = 250
  const val AppearanceSegmentFirstIndex = 0
  const val AppearanceSegmentZeroWidth = 0
  const val AppearanceSegmentZeroOffset = 0
  const val AppearanceSegmentMinSegmentCount = 1
  const val AppearanceSegmentTransitionLabel = "appearanceSegmentSelection"
  const val AppearanceSegmentIndicatorLabel = "appearanceSegmentIndicator"
  const val AppearanceSegmentLabelColorLabel = "appearanceSegmentLabelColor"
}
