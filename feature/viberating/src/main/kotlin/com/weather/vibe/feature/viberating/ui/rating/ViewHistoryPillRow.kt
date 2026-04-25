package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Emojis
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.viewHistoryLink
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.ButtonIconSize

@Composable
internal fun ViewHistoryPillRow(onClick: () -> Unit) {
  val label = viewHistoryLink()
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(shapes.pill)
      .clickable(
        role = Role.Button,
        onClickLabel = label,
        onClick = onClick
      )
      .padding(vertical = Padding.Small)
      .semantics(mergeDescendants = true) {},
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = Emojis.ViewHistoryPill,
      style = typography.titleSmall
    )
    Spacer(Modifier.width(Padding.Small))
    Text(
      text = label,
      style = typography.titleSmall,
      color = colors.accent,
      fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.width(Padding.ExtraSmall))
    Icon(
      modifier = Modifier.size(ButtonIconSize),
      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
      contentDescription = null,
      tint = colors.accent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ViewHistoryPillRow(onClick = {})
  }
}
