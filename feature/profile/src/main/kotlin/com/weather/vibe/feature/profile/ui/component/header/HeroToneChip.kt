package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.preview.HeroToneChipPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipBackground
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipPaddingHorizontal
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipPaddingVertical
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.briefToneClickLabel
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.briefToneLabel

@Composable
internal fun HeroToneChip(
  modifier: Modifier = Modifier,
  toneValue: String,
  onClick: () -> Unit
) {
  Row(
    modifier = modifier
      .clip(shapes.pill)
      .background(HeroChipBackground)
      .clickable(
        role = Role.Button,
        onClickLabel = briefToneClickLabel(),
        onClick = onClick
      )
      .padding(
        horizontal = HeroChipPaddingHorizontal,
        vertical = HeroChipPaddingVertical
      ),
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = briefToneLabel(),
      style = typography.labelMedium,
      color = colors.onAccent
    )
    Text(
      text = toneValue,
      style = typography.labelMedium.copy(fontWeight = SemiBold),
      color = colors.onAccent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HeroToneChipPreviewProvider::class)
  toneValue: String
) {
  WeatherVibeTheme {
    HeroToneChip(
      toneValue = toneValue,
      onClick = {}
    )
  }
}
