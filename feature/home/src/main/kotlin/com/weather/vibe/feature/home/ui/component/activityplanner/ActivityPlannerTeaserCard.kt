package com.weather.vibe.feature.home.ui.component.activityplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.IconActionButton
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.ActionButton
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.component.activityplanner.ActivityPlannerTeaserTexts.contentDescription
import com.weather.vibe.feature.home.ui.component.activityplanner.ActivityPlannerTeaserTexts.subtitle
import com.weather.vibe.feature.home.ui.component.activityplanner.ActivityPlannerTeaserTexts.title

@Composable
internal fun ActivityPlannerTeaserCard(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  VibeCard(
    modifier = modifier,
    onClick = onClick,
    onClickLabel = contentDescription()
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      TeaserText(modifier = Modifier.weight(1f))
      IconActionButton(
        modifier = Modifier.clearAndSetSemantics {},
        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowForward),
        contentDescription = contentDescription(),
        onClick = onClick,
        containerColor = colors.accent,
        contentColor = colors.onAccent,
        containerSize = ActionButton.Container,
        iconSize = ActionButton.DefaultIconSize
      )
    }
  }
}

@Composable
private fun TeaserText(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = title(),
      color = colors.onBackground,
      style = typography.titleMedium
    )
    Text(
      text = subtitle(),
      color = colors.onSurfaceVariant,
      style = typography.bodySmall
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ActivityPlannerTeaserCard(onClick = {})
  }
}
