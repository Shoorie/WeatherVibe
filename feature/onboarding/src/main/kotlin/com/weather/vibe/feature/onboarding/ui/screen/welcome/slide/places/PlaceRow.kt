package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.preview.welcome.slide.PlacesSamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CardHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CardRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CardVerticalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.EMOJI_SIZE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.EmojiToTextGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.NameToTagGap

@Composable
internal fun PlaceRow(
  modifier: Modifier = Modifier,
  place: PlaceCardUiState
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CardRadius))
      .background(colors.popupSurface)
      .padding(
        horizontal = CardHorizontalPadding,
        vertical = CardVerticalPadding
      ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(EmojiToTextGap)
  ) {
    Text(
      text = place.emoji,
      fontSize = EMOJI_SIZE.sp
    )
    PlaceDetails(
      modifier = Modifier.weight(1f),
      place = place
    )
    Text(
      text = place.temperature,
      style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
      color = colors.onSurface
    )
  }
}

@Composable
private fun PlaceDetails(
  modifier: Modifier = Modifier,
  place: PlaceCardUiState
) {
  Column(modifier = modifier) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(NameToTagGap)
    ) {
      Text(
        text = place.city,
        style = typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = colors.onSurface
      )
      PlaceTag(
        background = place.tagBackground,
        label = place.tagLabel
      )
    }
    Text(
      text = place.region,
      style = typography.labelSmall,
      color = colors.textTertiary
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PlaceRow(place = PlacesSamples.places().first())
  }
}
