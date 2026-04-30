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

  const val HALO_PULSE_DURATION_MS = 1400
  const val HALO_ALPHA_INNER = 0.35f
  const val HALO_ALPHA_MID = 0.18f
  const val HALO_ALPHA_OUTER = 0.05f
  const val PIN_SCALE_MIN = 0.92f
  const val PIN_SCALE_MAX = 1.08f
  const val PIN_ALPHA_MIN = 0.75f
  const val PIN_ALPHA_MAX = 1.0f
  const val INTENSITY_IDLE = 1.0f
  const val INTENSITY_REQUESTING = 0.7f
  const val INTENSITY_FETCHING = 1.4f
  const val INTENSITY_PERMANENTLY_DENIED = 0.55f
}
