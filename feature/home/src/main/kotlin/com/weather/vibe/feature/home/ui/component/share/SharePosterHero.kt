package com.weather.vibe.feature.home.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.share.ShareGradient
import com.weather.vibe.core.designsystem.theme.share.ShareGradientKey.SUNNY
import com.weather.vibe.core.designsystem.theme.share.ShareGradientPalette
import com.weather.vibe.core.designsystem.theme.share.ShareSurface.GlassHaloCenter
import com.weather.vibe.core.designsystem.theme.share.ShareSurface.GlassHaloMid
import com.weather.vibe.core.designsystem.theme.share.ShareSurface.HaloColorAlpha
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeroEmojiToTemperature
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeroGlowRadius
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.HeroTemperatureToCondition
import com.weather.vibe.feature.home.ui.HomeEmojis as Emojis

@Composable
internal fun SharePosterHero(
  modifier: Modifier = Modifier,
  conditionEmoji: String,
  conditionLabel: String,
  gradient: ShareGradient,
  temperature: String
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    HeroGlow(
      haloColor = gradient.glow,
      emoji = conditionEmoji
    )
    Spacer(modifier = Modifier.height(HeroEmojiToTemperature))
    Text(
      text = temperature,
      color = gradient.onSurface,
      style = posterTemperatureStyle(),
      textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(HeroTemperatureToCondition))
    Text(
      text = conditionLabel,
      color = gradient.onSurfaceSoft,
      style = posterConditionStyle(),
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun HeroGlow(
  haloColor: Color,
  emoji: String
) {

  val haloBrush = rememberHaloBrush(haloColor)
  val emojiStyle = posterEmojiStyle()

  Box(
    modifier = Modifier.size(HeroGlowRadius),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(HeroGlowRadius)
        .clip(CircleShape)
        .background(haloBrush)
    )
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = emoji,
      style = emojiStyle,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun rememberHaloBrush(haloColor: Color): Brush =
  remember(haloColor) {
    Brush.radialGradient(
      colors = listOf(
        GlassHaloCenter,
        GlassHaloMid,
        haloColor.copy(alpha = HaloColorAlpha),
        Color.Transparent
      )
    )
  }

@PreviewLightDark
@Composable
private fun Preview() {

  val gradient = ShareGradientPalette
    .gradientFor(SUNNY)

  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(gradient.background)
        .padding(Medium)
    ) {
      SharePosterHero(
        conditionEmoji = Emojis.sunny(),
        conditionLabel = "Mainly clear",
        gradient = gradient,
        temperature = "18°"
      )
    }
  }
}
