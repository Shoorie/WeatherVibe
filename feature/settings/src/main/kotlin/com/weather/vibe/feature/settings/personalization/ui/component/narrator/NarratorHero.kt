package com.weather.vibe.feature.settings.personalization.ui.component.narrator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.text.rememberTypedText
import com.weather.vibe.core.designsystem.components.text.withCaret
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.core.designsystem.theme.persona.PersonaColors
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.feature.settings.personalization.presentation.state.NarratorUiState
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorEyebrow
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorFreeBadge
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorPremiumBadge
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorSampleFooter
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.BubbleCorner
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.BubbleMinHeight
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.EyebrowAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.HeroCorner
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.HeroEmojiBox
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.HeroEmojiCorner
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.HeroPlayButton
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.HeroPlayIcon
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PremiumStarIcon
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.ScrimSoftAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.ScrimStrongAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.SubtitleAlpha

@Composable
internal fun NarratorHero(
  modifier: Modifier = Modifier,
  narrator: NarratorUiState
) {
  val colors = PersonaPalette.colorsFor(narrator.colorKey)
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(HeroCorner))
      .background(heroBrush(colors))
      .padding(Large)
  ) {
    NarratorHeader(isPremium = narrator.isPremium)
    Spacer(modifier = Modifier.height(Medium))
    NarratorIdentity(narrator = narrator)
    Spacer(modifier = Modifier.height(Medium))
    NarratorBubble(colors = colors, sample = narrator.sample, sampleKey = narrator.name)
  }
}

@Composable
private fun NarratorHeader(isPremium: Boolean) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = narratorEyebrow().uppercase(),
      style = typography.labelSmall,
      color = Color.White.copy(alpha = EyebrowAlpha)
    )
    NarratorBadge(isPremium = isPremium)
  }
}

@Composable
private fun NarratorBadge(isPremium: Boolean) {
  Row(
    modifier = Modifier
      .clip(CircleShape)
      .background(Color.White.copy(alpha = ScrimStrongAlpha))
      .padding(horizontal = Small, vertical = ExtraSmall),
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (isPremium) {
      Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(PremiumStarIcon)
      )
    }
    Text(
      text = if (isPremium) narratorPremiumBadge() else narratorFreeBadge(),
      style = typography.labelSmall,
      color = Color.White
    )
  }
}

@Composable
private fun NarratorIdentity(narrator: NarratorUiState) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Medium),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(HeroEmojiBox)
        .clip(RoundedCornerShape(HeroEmojiCorner))
        .background(Color.White.copy(alpha = ScrimSoftAlpha)),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = narrator.colorKey.emoji,
        style = typography.headlineMedium
      )
    }
    Column {
      Text(
        text = narrator.name,
        style = typography.titleLarge,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = narrator.subtitle,
        style = typography.bodySmall,
        color = Color.White.copy(alpha = SubtitleAlpha)
      )
    }
  }
}

@Composable
private fun NarratorBubble(
  colors: PersonaColors,
  sample: String,
  sampleKey: String
) {
  val typed = rememberTypedText(text = sample, key = sampleKey)
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(BubbleCorner))
      .background(Color.White)
      .padding(Medium)
  ) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = BubbleMinHeight),
      text = typed.withCaret(colors.accent),
      style = typography.bodyMedium,
      color = colors.ink
    )
    Spacer(modifier = Modifier.height(Small))
    Row(
      horizontalArrangement = Arrangement.spacedBy(Small),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(HeroPlayButton)
          .clip(CircleShape)
          .background(colors.accent),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Filled.PlayArrow,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(HeroPlayIcon)
        )
      }
      Text(
        text = narratorSampleFooter().uppercase(),
        style = typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = colors.accent
      )
    }
  }
}

private fun heroBrush(colors: PersonaColors): Brush =
  Brush.linearGradient(colors = listOf(colors.accentSecondary, colors.accent))

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    NarratorHero(
      modifier = Modifier.padding(Medium),
      narrator = NarratorUiState(
        colorKey = PersonaColorKey.CINEMATIC,
        isPremium = true,
        name = "Cinematic",
        sample = "Today's weather has the vibe of a good thriller's opening scene.",
        subtitle = "Atmospheric, vivid take on the day"
      )
    )
  }
}
