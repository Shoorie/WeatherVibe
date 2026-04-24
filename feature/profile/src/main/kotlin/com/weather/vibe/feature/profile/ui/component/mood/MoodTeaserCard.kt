package com.weather.vibe.feature.profile.ui.component.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.pill.VibePill
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.domain.viberating.model.Condition
import com.weather.vibe.feature.profile.R
import com.weather.vibe.feature.profile.presentation.MoodTeaserUiState
import com.weather.vibe.feature.profile.presentation.MoodTeaserViewModel
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodBadge
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodCta
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodTitle
import com.weather.vibe.feature.profile.ui.ProfileTextStyles
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MoodTeaserCard(
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null
) {
  val viewModel: MoodTeaserViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  MoodTeaserCardContent(
    modifier = modifier,
    state = state,
    onClick = onClick
  )
}

@Composable
private fun MoodTeaserCardContent(
  modifier: Modifier = Modifier,
  state: MoodTeaserUiState,
  onClick: (() -> Unit)?
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
    contentPadding = Padding.Zero
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(shapes.card)
        .background(heroGradient())
        .padding(Padding.Medium)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Padding.Medium)
      ) {
        MoodHeroBadge(state = state)
        Column(
          modifier = Modifier
            .weight(1f)
            .semantics(mergeDescendants = true) {},
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          TitleRow()
          SummaryLine(state = state)
          if (state.favoriteCondition != null) {
            Text(
              text = stringResource(
                R.string.profile_mood_favorite_format,
                conditionLabel(state.favoriteCondition)
              ),
              style = ProfileTextStyles.rowBody(),
              color = colors.onPrimaryContainer.copy(alpha = 0.82f)
            )
          }
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
private fun TitleRow() {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
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
}

@Composable
private fun SummaryLine(state: MoodTeaserUiState) {
  if (!state.hasData) {
    Text(
      text = stringResource(R.string.profile_mood_body_empty),
      style = ProfileTextStyles.rowBody(),
      color = colors.onPrimaryContainer.copy(alpha = 0.82f)
    )
    return
  }
  val average = "%.1f".format(state.averageRating)
  Text(
    text = stringResource(
      R.string.profile_mood_summary_format,
      average,
      state.totalEntries,
      daysPlural(state.totalEntries)
    ),
    style = ProfileTextStyles.rowBody(),
    color = colors.onPrimaryContainer,
    fontWeight = FontWeight.SemiBold
  )
}

@Composable
private fun MoodHeroBadge(state: MoodTeaserUiState) {
  val badgeColor = if (state.hasData) {
    ratingColor(state.averageRating.toInt().coerceAtLeast(1))
  } else {
    colors.accent.copy(alpha = 0.4f)
  }
  Box(
    modifier = Modifier
      .size(BadgeSize)
      .clip(CircleShape)
      .background(colors.glassSurface),
    contentAlignment = Alignment.Center
  ) {
    if (state.hasData) {
      Text(
        text = "%.1f".format(state.averageRating),
        style = WeatherVibeTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = badgeColor
      )
    } else {
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
}

@Composable
private fun heroGradient(): Brush = Brush.linearGradient(
  colors = listOf(
    colors.primaryContainer,
    colors.accent.copy(alpha = 0.4f)
  )
)

@Composable
private fun conditionLabel(condition: Condition): String =
  stringResource(
    when (condition) {
      Condition.SUNNY -> R.string.profile_condition_sunny
      Condition.PARTLY_CLOUDY -> R.string.profile_condition_partly_cloudy
      Condition.CLOUDY -> R.string.profile_condition_cloudy
      Condition.RAIN -> R.string.profile_condition_rain
      Condition.SNOW -> R.string.profile_condition_snow
      Condition.NIGHT -> R.string.profile_condition_night
    }
  )

@Composable
private fun daysPlural(count: Int): String {
  val resId = when {
    count == 1 -> R.string.profile_mood_days_one
    count % 10 in 2..4 && count % 100 !in 12..14 -> R.string.profile_mood_days_few
    else -> R.string.profile_mood_days_many
  }
  return stringResource(resId)
}

private fun ratingDotColor(rating: Int): Color = when (rating) {
  1 -> RatingColors.Rating1
  2 -> RatingColors.Rating2
  3 -> RatingColors.Rating3
  4 -> RatingColors.Rating4
  else -> RatingColors.Rating5
}

private val BadgeSize = 56.dp
private val DotSize = 6.dp

@PreviewLightDark
@Composable
private fun PreviewEmpty() {
  WeatherVibeTheme {
    MoodTeaserCardContent(
      state = MoodTeaserUiState.EMPTY,
      onClick = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewLoaded() {
  WeatherVibeTheme {
    MoodTeaserCardContent(
      state = MoodTeaserUiState(
        hasData = true,
        averageRating = 4.2,
        totalEntries = 47,
        favoriteCondition = Condition.SUNNY
      ),
      onClick = {}
    )
  }
}
