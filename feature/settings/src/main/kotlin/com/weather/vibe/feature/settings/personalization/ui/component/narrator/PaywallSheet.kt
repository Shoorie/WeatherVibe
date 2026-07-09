package com.weather.vibe.feature.settings.personalization.ui.component.narrator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.ads.rewarded.rememberRewardedAdController
import com.weather.vibe.core.designsystem.components.text.rememberTypedText
import com.weather.vibe.core.designsystem.components.text.withCaret
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.core.designsystem.theme.persona.PersonaColors
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.domain.ads.placement.AdPlacement.ToneUnlockRewarded
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.personalization.presentation.state.PaywallUiState
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.paywallMaybeLater
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.paywallPremiumSubtitle
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.paywallPremiumTone
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.paywallUnlockPremium
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.paywallWatchVideo
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.paywallWatchVideoSubtitle
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.ActionIcon
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.BubbleCorner
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.BubbleMinHeight
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.LockedAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PaywallActionIcon
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PaywallCorner
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PaywallEmojiBox
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PremiumStarIcon
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PremiumSubtitleAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.ScrimSoftAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.ScrimStrongAlpha
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.TightSpacing
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.WatchSpinnerStroke

@Composable
internal fun PaywallSheet(
  modifier: Modifier = Modifier,
  onBuyPremium: () -> Unit,
  onDismiss: () -> Unit,
  onUnlockedViaAd: () -> Unit,
  paywall: PaywallUiState
) {
  val personaColors = PersonaPalette.colorsFor(paywall.colorKey)
  val controller = rememberRewardedAdController()
  val scope = rememberCoroutineScope()
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = Medium, vertical = Small),
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    PaywallHeader(colors = personaColors, paywall = paywall)
    PaywallPremiumButton(
      enabled = !controller.isWatching,
      onBuyPremium = onBuyPremium
    )
    PaywallWatchVideoButton(
      accent = personaColors.accent,
      isLoading = controller.isWatching,
      name = paywall.name,
      onWatch = { controller.rewardOnWatch(scope, ToneUnlockRewarded, onUnlockedViaAd) }
    )
    Text(
      text = paywallMaybeLater(),
      style = typography.labelMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .fillMaxWidth()
        .clip(shapes.card)
        .clickable(role = Role.Button, onClick = onDismiss)
        .padding(Small)
    )
  }
}

@Composable
private fun PaywallHeader(
  colors: PersonaColors,
  paywall: PaywallUiState
) {
  val typed = rememberTypedText(text = paywall.sample, key = "sheet-${paywall.name}")
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(PaywallCorner))
      .background(Brush.linearGradient(listOf(colors.accentSecondary, colors.accent)))
      .padding(Large),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(Medium),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(PaywallEmojiBox)
          .clip(RoundedCornerShape(BubbleCorner))
          .background(Color.White.copy(alpha = ScrimSoftAlpha)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = paywall.colorKey.emoji,
          style = typography.headlineMedium
        )
      }
      Column(verticalArrangement = Arrangement.spacedBy(ExtraSmall)) {
        Row(
          modifier = Modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = ScrimStrongAlpha))
            .padding(horizontal = Small, vertical = TightSpacing),
          horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(PremiumStarIcon)
          )
          Text(
            text = paywallPremiumTone().uppercase(),
            style = typography.labelSmall,
            color = Color.White
          )
        }
        Text(
          text = paywall.name,
          style = typography.titleLarge,
          color = Color.White
        )
      }
    }
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(BubbleCorner))
        .background(Color.White)
        .padding(Medium)
        .heightIn(min = BubbleMinHeight),
      text = typed.withCaret(colors.accent),
      style = typography.bodyMedium,
      color = colors.ink
    )
  }
}

@Composable
private fun PaywallPremiumButton(
  enabled: Boolean,
  onBuyPremium: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(PersonaPalette.premiumBrush())
      .alpha(if (enabled) 1f else LockedAlpha)
      .clickable(enabled = enabled, role = Role.Button, onClick = onBuyPremium)
      .padding(Medium),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(TightSpacing)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(Small),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(ActionIcon)
      )
      Text(
        text = paywallUnlockPremium(),
        style = typography.titleSmall,
        color = Color.White
      )
    }
    Text(
      text = paywallPremiumSubtitle(),
      style = typography.bodySmall,
      color = Color.White.copy(alpha = PremiumSubtitleAlpha),
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun PaywallWatchVideoButton(
  accent: Color,
  isLoading: Boolean,
  name: String,
  onWatch: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.rowSurface)
      .border(Stroke.Border, colors.outlineVariant, shapes.card)
      .clickable(enabled = !isLoading, role = Role.Button, onClick = onWatch)
      .padding(Medium),
    horizontalArrangement = Arrangement.spacedBy(Medium),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(PaywallActionIcon)
        .clip(CircleShape)
        .background(accent),
      contentAlignment = Alignment.Center
    ) {
      if (isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.size(ActionIcon),
          color = Color.White,
          strokeWidth = WatchSpinnerStroke
        )
      } else {
        Icon(
          imageVector = Icons.Filled.PlayArrow,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(ActionIcon)
        )
      }
    }
    Column {
      Text(
        text = paywallWatchVideo(),
        style = typography.titleSmall,
        color = colors.onSurface
      )
      Text(
        text = paywallWatchVideoSubtitle(name),
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PaywallSheet(
      onBuyPremium = {},
      onDismiss = {},
      onUnlockedViaAd = {},
      paywall = PaywallUiState(
        colorKey = PersonaColorKey.COACH,
        name = "Coach",
        sample = "You're in solid shape today. Light wind, perfect for an outdoor workout.",
        tone = BriefTone.COACH
      )
    )
  }
}
