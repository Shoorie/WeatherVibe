package com.weather.vibe.feature.profile.ui.component.footer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.footer

@Composable
internal fun ProfileFooter(modifier: Modifier = Modifier) {
  Text(
    modifier = modifier.fillMaxWidth(),
    text = footer(),
    style = typography.labelSmall,
    color = colors.textTertiary,
    textAlign = TextAlign.Center
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ProfileFooter()
  }
}
