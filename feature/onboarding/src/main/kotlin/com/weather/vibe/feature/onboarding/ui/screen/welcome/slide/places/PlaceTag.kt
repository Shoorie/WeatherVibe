package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.category.CategoryTagPalette.Sky
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.TagHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.TagRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.TagVerticalPadding

@Composable
internal fun PlaceTag(
  modifier: Modifier = Modifier,
  background: Color,
  label: String
) {
  Text(
    modifier = modifier
      .clip(RoundedCornerShape(TagRadius))
      .background(background)
      .padding(
        horizontal = TagHorizontalPadding,
        vertical = TagVerticalPadding
      ),
    text = label,
    style = typography.labelSmall
      .copy(fontWeight = Bold),
    color = Color.White
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PlaceTag(
      background = Sky,
      label = "Home"
    )
  }
}
