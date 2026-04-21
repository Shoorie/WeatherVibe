package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.preview.ProfileHeroPreview
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipAlpha
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipPaddingHorizontal
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroChipPaddingVertical
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.briefToneLabel
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.editHeaderClickLabel
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun ProfileHero(
  modifier: Modifier = Modifier,
  header: ProfileHeaderUiState,
  onEditClick: () -> Unit
) {

  val accentColor = colors.accent
  val accentDarkColor = colors.accentDark
  val gradientBrush = remember(accentColor, accentDarkColor) {
    Brush.linearGradient(colors = listOf(accentColor, accentDarkColor))
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .shadow(
        elevation = Elevation.Card,
        shape = shapes.cardLarge,
        clip = false
      )
      .clip(shapes.cardLarge)
      .background(gradientBrush)
      .clickable(
        role = Role.Button,
        onClickLabel = editHeaderClickLabel(),
        onClick = onEditClick
      )
      .padding(Large)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(Medium)) {
      HeroTopRow(header = header)
      if (header.quote.isNotBlank()) {
        Text(
          text = header.quote,
          textAlign = TextAlign.Center,
          style = ProfileTextStyles.heroQuote(),
          color = colors.onAccent
        )
      }
      if (header.briefToneLabel.isNotBlank()) {
        BriefToneRow(value = header.briefToneLabel)
      }
    }
  }
}

@Composable
private fun HeroTopRow(header: ProfileHeaderUiState) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) { heading() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Medium)
  ) {
    ProfileAvatar(initial = header.avatarInitial)
    HeaderTexts(
      modifier = Modifier.weight(1f),
      greeting = header.greeting,
      subtitle = header.subtitle
    )
    Icon(
      modifier = Modifier.size(IconSize.Small),
      imageVector = Icons.Default.Edit,
      contentDescription = null,
      tint = colors.onAccent
    )
  }
}

@Composable
private fun HeaderTexts(
  modifier: Modifier = Modifier,
  greeting: String,
  subtitle: String
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      text = greeting,
      style = ProfileTextStyles.greeting(),
      color = colors.onAccent
    )
    Text(
      text = subtitle,
      style = ProfileTextStyles.subtitle(),
      color = colors.onAccent
    )
  }
}

@Composable
private fun BriefToneRow(value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {},
    horizontalArrangement = Arrangement.spacedBy(Small, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = briefToneLabel(),
      style = ProfileTextStyles.heroChipLabel(),
      color = colors.onAccent
    )
    HeroChip(value = value)
  }
}

@Composable
private fun HeroChip(value: String) {
  Text(
    modifier = Modifier
      .clip(shapes.pill)
      .background(colors.onAccent.copy(alpha = HeroChipAlpha))
      .padding(
        horizontal = HeroChipPaddingHorizontal,
        vertical = HeroChipPaddingVertical
      ),
    text = value,
    style = ProfileTextStyles.heroChipValue(),
    color = colors.onAccent
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ProfileHeroPreview::class)
  header: ProfileHeaderUiState
) {
  WeatherVibeTheme {
    ProfileHero(
      header = header,
      onEditClick = {}
    )
  }
}
