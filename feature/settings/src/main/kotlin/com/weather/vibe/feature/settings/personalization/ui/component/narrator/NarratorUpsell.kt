package com.weather.vibe.feature.settings.personalization.ui.component.narrator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorUnlockAll
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorUnlockAllSubtitle
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.ActionIcon

@Composable
internal fun NarratorUpsell(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  premiumToneCount: Int
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(shapes.card)
        .background(PersonaPalette.premiumBrush())
        .clickable(role = Role.Button, onClick = onClick)
        .padding(Medium),
      horizontalArrangement = Arrangement.spacedBy(Small, Alignment.CenterHorizontally),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(ActionIcon)
      )
      Text(
        text = narratorUnlockAll(premiumToneCount),
        style = typography.titleSmall,
        color = Color.White
      )
    }
    Text(
      text = narratorUnlockAllSubtitle(),
      style = typography.bodySmall,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(top = ExtraSmall)
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    NarratorUpsell(
      modifier = Modifier.padding(Medium),
      onClick = {},
      premiumToneCount = 5
    )
  }
}
