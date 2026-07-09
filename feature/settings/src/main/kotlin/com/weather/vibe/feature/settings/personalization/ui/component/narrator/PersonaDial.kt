package com.weather.vibe.feature.settings.personalization.ui.component.narrator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.core.designsystem.theme.persona.PersonaColors
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonaUiState
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.personaLockedContentDescription
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.DialBadge
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.DialBadgeBorder
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.DialBadgeIcon
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.DialCircle
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.DialRingStroke
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.DialWidth
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.LockedAlpha

@Composable
internal fun PersonaDial(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  persona: PersonaUiState
) {
  val personaColors = PersonaPalette.colorsFor(persona.colorKey)
  Column(
    modifier = modifier.width(DialWidth),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Box(contentAlignment = Alignment.Center) {
      PersonaCircle(
        onClick = onClick,
        persona = persona,
        personaColors = personaColors
      )
      PersonaBadge(persona = persona, accent = personaColors.accent)
    }
    Text(
      text = persona.label,
      style = typography.labelSmall,
      color = if (persona.isSelected) personaColors.accent else colors.onSurfaceVariant,
      fontWeight = if (persona.isSelected) FontWeight.Bold else FontWeight.Medium,
      textAlign = TextAlign.Center,
      maxLines = 1
    )
  }
}

@Composable
private fun PersonaCircle(
  onClick: () -> Unit,
  persona: PersonaUiState,
  personaColors: PersonaColors
) {
  val lockedDescription = personaLockedContentDescription().takeIf { persona.isLocked }
  Box(
    modifier = Modifier
      .size(DialCircle)
      .clip(CircleShape)
      .background(if (persona.isSelected) personaColors.soft else colors.surfaceVariant)
      .selectedRing(isSelected = persona.isSelected, accent = personaColors.accent)
      .clickable(
        onClickLabel = lockedDescription,
        role = Role.Button,
        onClick = onClick
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = persona.colorKey.emoji,
      style = typography.titleLarge,
      modifier = Modifier.alpha(if (persona.isLocked) LockedAlpha else 1f)
    )
  }
}

@Composable
private fun BoxScope.PersonaBadge(
  persona: PersonaUiState,
  accent: Color
) {
  when {
    persona.isLocked -> BadgeCircle(alignment = Alignment.BottomEnd, background = accent) {
      Icon(
        imageVector = Icons.Filled.Lock,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(DialBadgeIcon)
      )
    }
    persona.isPremium -> BadgeCircle(alignment = Alignment.TopEnd, background = accent) {
      Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(DialBadgeIcon)
      )
    }
  }
}

@Composable
private fun BoxScope.BadgeCircle(
  alignment: Alignment,
  background: Color,
  content: @Composable () -> Unit
) {
  Box(
    modifier = Modifier
      .align(alignment)
      .size(DialBadge)
      .clip(CircleShape)
      .background(Color.White)
      .border(DialBadgeBorder, Color.White, CircleShape)
      .clip(CircleShape)
      .background(background),
    contentAlignment = Alignment.Center
  ) {
    content()
  }
}

private fun Modifier.selectedRing(isSelected: Boolean, accent: Color): Modifier =
  if (isSelected) border(DialRingStroke, accent, CircleShape) else this

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PersonaDial(
      onClick = {},
      persona = PersonaUiState(
        colorKey = PersonaColorKey.COACH,
        isLocked = true,
        isPremium = true,
        isSelected = false,
        label = "Coach",
        tone = BriefTone.COACH
      )
    )
  }
}
