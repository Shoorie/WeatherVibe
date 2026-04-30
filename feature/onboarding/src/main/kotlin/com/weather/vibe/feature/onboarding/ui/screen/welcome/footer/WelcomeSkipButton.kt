package com.weather.vibe.feature.onboarding.ui.screen.welcome.footer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.footer.FooterDefaults.SkipMinTouchSize

@Composable
internal fun WelcomeSkipButton(
  modifier: Modifier = Modifier,
  label: String,
  onClick: () -> Unit
) {
  TextButton(
    modifier = modifier
      .defaultMinSize(
        minWidth = SkipMinTouchSize,
        minHeight = SkipMinTouchSize
      ),
    contentPadding = PaddingValues(),
    onClick = onClick
  ) {
    Text(
      text = label,
      style = typography.titleSmall,
      color = colors.textTertiary
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WelcomeSkipButton(
      label = "Skip",
      onClick = {}
    )
  }
}
