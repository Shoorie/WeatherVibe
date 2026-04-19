package com.weather.vibe.feature.onboarding.ui.screen

import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small

internal object OnboardingDefaults {

  val HaloDiameter = 220.dp
  val PinSize = 56.dp
  val ContentMaxWidth = 360.dp
  val HaloToHeadline = ExtraLarge
  val HeadlineToSubtitle = 12.dp
  val SubtitleToPrimaryCta = 40.dp
  val PrimaryToSecondaryCta = Small
  val SecondaryCtaToPrivacy = ExtraLarge

  const val HaloPulseDurationMs = 1400
  const val HaloAlphaInner = 0.35f
  const val HaloAlphaMid = 0.18f
  const val HaloAlphaOuter = 0.05f
  const val PinScaleMin = 0.92f
  const val PinScaleMax = 1.08f
  const val PinAlphaMin = 0.75f
  const val PinAlphaMax = 1.0f
  const val IntensityIdle = 1.0f
  const val IntensityRequesting = 0.7f
  const val IntensityFetching = 1.4f
  const val IntensityPermanentlyDenied = 0.55f
}
