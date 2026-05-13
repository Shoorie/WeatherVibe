package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class ReadyOrientationSpacing(
  val headlineToCards: Dp,
  val helloToHeadline: Dp,
  val topInset: Dp
) {

  companion object {

    val Portrait: ReadyOrientationSpacing =
      ReadyOrientationSpacing(
        headlineToCards = 24.dp,
        helloToHeadline = 40.dp,
        topInset = 80.dp
      )

    val Landscape: ReadyOrientationSpacing =
      ReadyOrientationSpacing(
        headlineToCards = 24.dp,
        helloToHeadline = 16.dp,
        topInset = 32.dp
      )

    fun forOrientation(isLandscape: Boolean): ReadyOrientationSpacing =
      if (isLandscape) Landscape else Portrait
  }
}
