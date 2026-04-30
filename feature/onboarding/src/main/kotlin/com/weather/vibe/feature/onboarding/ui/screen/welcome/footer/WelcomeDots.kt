package com.weather.vibe.feature.onboarding.ui.screen.welcome.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.lerp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.DOT_ROUND_PERCENT
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.DotActiveWidth
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.DotInactiveSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.DotsGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.INACTIVE_DOT_ALPHA
import kotlin.math.absoluteValue

@Composable
internal fun WelcomeDots(
  modifier: Modifier = Modifier,
  activePosition: Float,
  total: Int
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(DotsGap),
    verticalAlignment = Alignment.CenterVertically
  ) {
    repeat(total) { index ->
      WelcomeDot(
        activeFraction = activeFractionFor(
          index = index,
          position = activePosition
        )
      )
    }
  }
}

@Composable
private fun WelcomeDot(activeFraction: Float) {

  val active = colors.accent
  val inactive = colors.accent.copy(alpha = INACTIVE_DOT_ALPHA)
  val color = lerp(
    start = inactive,
    stop = active,
    fraction = activeFraction
  )
  val width = lerp(
    start = DotInactiveSize,
    stop = DotActiveWidth,
    fraction = activeFraction
  )

  Box(
    modifier = Modifier
      .height(DotInactiveSize)
      .width(width)
      .clip(RoundedCornerShape(percent = DOT_ROUND_PERCENT))
      .background(color)
      .size(width = width, height = DotInactiveSize)
  )
}

private fun activeFractionFor(index: Int, position: Float): Float {
  val distance = (position - index)
    .absoluteValue.coerceAtMost(maximumValue = 1f)
  return 1f - distance
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WelcomeDots(
      activePosition = 1f,
      total = 5
    )
  }
}
