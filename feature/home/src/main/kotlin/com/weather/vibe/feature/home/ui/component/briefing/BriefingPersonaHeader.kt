package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.feature.home.presentation.state.BriefingPersonaUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Limit
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingPersonaReads
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.PersonaBadgeCornerRadius
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.PersonaBadgeFillAlpha
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.PersonaBadgeSize

@Composable
internal fun BriefingPersonaHeader(state: BriefingUiState) {
  val persona = state.personaOrNull() ?: return
  val accent = PersonaPalette.colorsFor(persona.colorKey).accent
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(PersonaBadgeSize)
        .clip(RoundedCornerShape(PersonaBadgeCornerRadius))
        .background(accent.copy(alpha = PersonaBadgeFillAlpha)),
      contentAlignment = Alignment.Center
    ) {
      Text(text = persona.emoji, style = typography.titleMedium)
    }
    Text(
      text = aiBriefingPersonaReads(),
      style = typography.labelMedium,
      color = accent
    )
  }
  Spacer(modifier = Modifier.height(Small))
}

private fun BriefingUiState.personaOrNull(): BriefingPersonaUiState? = when (this) {
  is Loaded -> persona
  is Limit -> persona
  else -> null
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingPersonaHeader(
      state = Loaded(
        persona = BriefingPersonaUiState(
          colorKey = PersonaColorKey.COACH,
          emoji = "🏋️"
        ),
        text = "Sample"
      )
    )
  }
}
