package com.weather.vibe.feature.onboarding.ui.screen.welcome.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.VibePrimaryButton
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.DotsToButton
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.FADE_MID_ALPHA
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.FADE_MID_STOP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.FooterBottomPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.FooterHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.FooterTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.PrimaryButtonHeight

@Composable
internal fun WelcomeFooter(
  modifier: Modifier = Modifier,
  ctaLabel: String,
  dotPosition: Float,
  totalSlides: Int,
  onCtaClick: () -> Unit
) {

  val baseColor = colors.backgroundGradientEnd
  val fadeBrush = remember(baseColor) {
    footerFadeBrush(baseColor = baseColor)
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(fadeBrush)
      .padding(
        start = FooterHorizontalPadding,
        end = FooterHorizontalPadding,
        top = FooterTopPadding,
        bottom = FooterBottomPadding
      )
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      WelcomeDots(
        activePosition = dotPosition,
        total = totalSlides
      )
      Spacer(modifier = Modifier.height(DotsToButton))
      VibePrimaryButton(
        modifier = Modifier.height(PrimaryButtonHeight),
        text = ctaLabel,
        onClick = onCtaClick
      )
    }
  }
}

private fun footerFadeBrush(baseColor: Color): Brush =
  Brush.verticalGradient(
    colorStops = arrayOf(
      0f to baseColor.copy(alpha = 0f),
      FADE_MID_STOP to baseColor.copy(alpha = FADE_MID_ALPHA),
      1f to baseColor
    )
  )

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WelcomeFooter(
      ctaLabel = "Next",
      dotPosition = 1f,
      totalSlides = 5,
      onCtaClick = {}
    )
  }
}
