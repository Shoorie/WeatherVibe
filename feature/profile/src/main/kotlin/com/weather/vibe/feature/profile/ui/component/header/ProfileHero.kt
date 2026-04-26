package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.preview.ProfileHeaderPreviewProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipMarginTop
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroPaddingBottom
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroPaddingHorizontal
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroPaddingTop
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroShape
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.editHeaderClickLabel

@Composable
internal fun ProfileHero(
  modifier: Modifier = Modifier,
  header: ProfileHeaderUiState,
  onEditClick: () -> Unit,
  onBriefToneClick: () -> Unit
) {
  val accent = colors.accent
  val accentDark = colors.accentDark
  val gradient = remember(accent, accentDark) {
    Brush.linearGradient(colors = listOf(accent, accentDark))
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .shadow(elevation = Elevation.Card, shape = HeroShape, clip = false)
      .clip(HeroShape)
      .background(gradient)
      .clickable(
        role = Role.Button,
        onClickLabel = editHeaderClickLabel(),
        onClick = onEditClick
      )
  ) {
    HeroDecorations()
    Column(
      modifier = Modifier
        .padding(horizontal = HeroPaddingHorizontal)
        .padding(top = HeroPaddingTop, bottom = HeroPaddingBottom),
      verticalArrangement = Arrangement.spacedBy(HeroChipMarginTop)
    ) {
      HeroGreetingRow(
        header = header,
        onEditClick = onEditClick
      )
      if (header.briefToneLabel.isNotBlank()) {
        HeroToneChip(
          toneValue = header.briefToneLabel,
          onClick = onBriefToneClick
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ProfileHeaderPreviewProvider::class)
  header: ProfileHeaderUiState
) {
  WeatherVibeTheme {
    ProfileHero(
      header = header,
      onEditClick = {},
      onBriefToneClick = {}
    )
  }
}
