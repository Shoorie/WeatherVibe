package com.weather.vibe.feature.profile.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.shape.RoundedCornerShape
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
  val HeroEditButtonHitTarget = 48.dp
  val HeroEditButtonVisualSize = 30.dp
  val HeroEditButtonIconSize = 16.dp
  val HeroChipPaddingHorizontal = 12.dp
  val HeroChipPaddingVertical = 8.dp
  val HeroChipMarginTop = 14.dp
  val HeroGreetingToHandSpacing = 6.dp
  val HeroAvatarToTextsSpacing = 14.dp
  val HeroDecorationCloudWidth = 90.dp
  val HeroDecorationCloudHeight = 58.dp
  val HeroDecorationCloudOffsetX = 10.dp
  val HeroDecorationCloudOffsetY = (-10).dp
  val HeroDecorationSunSize = 40.dp
  val HeroDecorationSunOffsetX = (-16).dp
  val HeroDecorationSunOffsetY = 50.dp
  val HeroDecorationCloudFloatRangeDp = 3f
  const val HeroDecorationCloudFloatDurationMs = 4000
  const val HeroDecorationCloudAlpha = 0.14f
  const val HeroDecorationSunAlpha = 0.22f
  const val HeroChipAlpha = 0.2f
  const val HeroEditButtonAlpha = 0.16f

  val WavingHandFontSize = 22.sp
  const val WavingHandDurationMs = 2600
  val WavingHandEasing = LinearEasing

  val StatTileRadius = 20.dp
  val StatTilePaddingHorizontal = 8.dp
  val StatTilePaddingVertical = 12.dp
  val StatTileEmojiFontSize = 20.sp
  val StatTileGap = 4.dp

  val VibeRowRadius = 22.dp
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
  val AppearanceSegmentChipRadius = 10.dp
  val AppearanceSegmentInnerPadding = 4.dp
  val AppearanceSegmentChipPaddingHorizontal = 10.dp
  val AppearanceSegmentChipPaddingVertical = 8.dp
  val AppearanceSegmentTopSpacing = 12.dp
  val AppearanceSegmentMinHeight = 36.dp
  const val AppearanceSegmentBackgroundAlpha = 0.06f
  const val AppearanceSegmentTransitionMs = 250

  val FooterTopSpacing = 20.dp
}
