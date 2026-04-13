package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.BrandButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.ui.HomeResources.Painters.ytMusicIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.openInYtMusic

private val YtMusicRed = Color(0xFFFF0000)

@Composable
internal fun YtMusicButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  BrandButton(
    modifier = modifier,
    icon = ytMusicIcon(),
    text = openInYtMusic(),
    containerColor = YtMusicRed,
    onClick = onClick
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    YtMusicButton(
      modifier = Modifier.padding(Medium),
      onClick = {}
    )
  }
}
