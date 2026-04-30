package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.CardHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.CardRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.CardVerticalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.HeroToOutfitGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.OutfitChipHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.OutfitChipIconGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.OutfitChipIconSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.OutfitChipVertical
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.PILL_ROUND_PERCENT
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.PillHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.PillToHeroGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.PillVerticalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.SunToTempGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.TempToCaptionGap

@Composable
internal fun TalkHeroCard(
  modifier: Modifier = Modifier,
  caption: String,
  outfitText: String,
  pillLabel: String,
  temperature: String
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CardRadius))
      .background(colors.popupSurface)
      .padding(
        horizontal = CardHorizontalPadding,
        vertical = CardVerticalPadding
      ),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    HeroPill(label = pillLabel)
    Spacer(modifier = Modifier.height(PillToHeroGap))
    HeroSunAndTemperature(temperature = temperature)
    Spacer(modifier = Modifier.height(TempToCaptionGap))
    HeroCaption(caption = caption)
    Spacer(modifier = Modifier.height(HeroToOutfitGap))
    HeroOutfitChip(outfitText = outfitText)
  }
}

@Composable
private fun HeroPill(label: String) {
  Text(
    modifier = Modifier
      .clip(RoundedCornerShape(percent = PILL_ROUND_PERCENT))
      .background(colors.primaryContainer)
      .padding(
        horizontal = PillHorizontalPadding,
        vertical = PillVerticalPadding
      ),
    text = label,
    style = typography.labelMedium
      .copy(fontWeight = SemiBold),
    color = colors.onPrimaryContainer
  )
}

@Composable
private fun HeroSunAndTemperature(temperature: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(SunToTempGap)
  ) {
    SpinningSunIcon()
    Text(
      text = temperature,
      style = typography.displayLarge
        .copy(fontWeight = Light),
      color = colors.onSurface,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun HeroCaption(caption: String) {
  Text(
    text = caption,
    style = typography.labelMedium.copy(fontWeight = SemiBold),
    color = colors.onSurfaceVariant,
    textAlign = TextAlign.Center
  )
}

@Composable
private fun HeroOutfitChip(outfitText: String) {
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(percent = PILL_ROUND_PERCENT))
      .background(colors.primaryContainer)
      .padding(
        horizontal = OutfitChipHorizontal,
        vertical = OutfitChipVertical
      ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(OutfitChipIconGap)
  ) {
    Icon(
      modifier = Modifier.size(OutfitChipIconSize),
      imageVector = Icons.Filled.Star,
      contentDescription = null,
      tint = colors.accent
    )
    Text(
      text = outfitText,
      style = typography.titleSmall
        .copy(fontWeight = SemiBold),
      color = colors.onPrimaryContainer
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    TalkHeroCard(
      caption = "sunny",
      outfitText = "Perfect time to head out",
      pillLabel = "Your vibe today",
      temperature = "24°"
    )
  }
}
