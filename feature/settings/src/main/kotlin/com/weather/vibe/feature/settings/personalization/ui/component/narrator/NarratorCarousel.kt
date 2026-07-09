package com.weather.vibe.feature.settings.personalization.ui.component.narrator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.personalization.presentation.state.PersonaUiState
import com.weather.vibe.feature.settings.personalization.preview.NarratorCarouselPreviewProvider
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorChange
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationResources.Texts.narratorPremiumCount
import com.weather.vibe.feature.settings.personalization.ui.component.narrator.NarratorDefaults.PremiumStarIcon
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun NarratorCarousel(
  modifier: Modifier = Modifier,
  onPersonaClick: (PersonaUiState) -> Unit,
  personas: ImmutableList<PersonaUiState>,
  premiumToneCount: Int,
  showPremiumCount: Boolean
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = Medium, vertical = ExtraSmall),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = narratorChange().uppercase(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
      if (showPremiumCount) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = colors.accent,
            modifier = Modifier.size(PremiumStarIcon)
          )
          Text(
            text = narratorPremiumCount(premiumToneCount),
            style = typography.labelSmall,
            color = colors.accent
          )
        }
      }
    }
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
      contentPadding = PaddingValues(horizontal = Medium)
    ) {
      items(items = personas, key = { it.tone.name }) { persona ->
        PersonaDial(
          onClick = { onPersonaClick(persona) },
          persona = persona
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(NarratorCarouselPreviewProvider::class)
  personas: ImmutableList<PersonaUiState>
) {
  WeatherVibeTheme {
    NarratorCarousel(
      onPersonaClick = {},
      personas = personas,
      premiumToneCount = 5,
      showPremiumCount = true
    )
  }
}
