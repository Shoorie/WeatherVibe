package com.weather.vibe.feature.profile.ui.component.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.pill.VibePill
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodBadge
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodCta
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodTitle
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun MoodTeaserCard(
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null
) {
  val clickLabel = moodCta()
  val cardModifier = modifier.then(
    if (onClick != null) {
      Modifier.clickable(role = Role.Button, onClickLabel = clickLabel, onClick = onClick)
    } else {
      Modifier
    }
  )
  VibeCard(
    modifier = cardModifier,
    shape = shapes.card,
    containerColor = Color.Transparent,
    contentPadding = Medium
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(shapes.card)
        .background(heroGradient())
        .padding(Medium)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Medium)
      ) {
        MoodColorRing()
        Column(
          modifier = Modifier
            .weight(1f)
            .semantics(mergeDescendants = true) {},
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Small)
          ) {
            Text(
              text = moodTitle(),
              style = ProfileTextStyles.sectionTitle(),
              color = colors.onPrimaryContainer,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.semantics { heading() }
            )
            VibePill(
              text = moodBadge(),
              containerColor = colors.accent,
              contentColor = colors.onAccent
            )
          }
          Text(
            text = moodBody(),
            style = ProfileTextStyles.rowBody(),
            color = colors.onPrimaryContainer.copy(alpha = 0.82f)
          )
        }
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
          contentDescription = null,
          tint = colors.onPrimaryContainer.copy(alpha = 0.7f)
        )
      }
    }
  }
}

@Composable
private fun MoodColorRing() {
  Box(
    modifier = Modifier
      .size(RingSize)
      .clip(CircleShape)
      .background(colors.glassSurface),
    contentAlignment = Alignment.Center
  ) {
    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
      for (rating in RatingColors.MIN_RATING..RatingColors.MAX_RATING) {
        Box(
          modifier = Modifier
            .size(DotSize)
            .clip(CircleShape)
            .background(ratingDotColor(rating))
        )
      }
    }
  }
}

@Composable
private fun heroGradient(): Brush = Brush.linearGradient(
  colors = listOf(
    colors.primaryContainer,
    colors.accent.copy(alpha = 0.35f)
  )
)

private fun ratingDotColor(rating: Int): Color = when (rating) {
  1 -> RatingColors.Rating1
  2 -> RatingColors.Rating2
  3 -> RatingColors.Rating3
  4 -> RatingColors.Rating4
  else -> RatingColors.Rating5
}

private val RingSize = 48.dp
private val DotSize = 6.dp

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    MoodTeaserCard(onClick = {})
  }
}
