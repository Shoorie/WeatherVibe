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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.feature.profile.R
import com.weather.vibe.feature.profile.presentation.MoodTeaserUiState
import com.weather.vibe.feature.profile.presentation.MoodTeaserViewModel
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodCta
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodTitle
import com.weather.vibe.feature.profile.ui.ProfileTextStyles
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.AverageRatingFormat
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.BadgeSize
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.DotSize
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.DotSpacing
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.FadedAccentAlpha
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.TitleRowSpacing
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
          verticalArrangement = Arrangement.spacedBy(TitleRowSpacing)
        ) {
          Text(
            text = moodTitle(),
            style = ProfileTextStyles.sectionTitle(),
            color = colors.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.semantics { heading() }
          )
          SummaryLine(state = state)
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
      state.dayCount,
      daysPlural(state.dayCount)
    ),
    style = ProfileTextStyles.rowBody(),
    color = colors.onPrimaryContainer,
    fontWeight = FontWeight.SemiBold
  )
}

@Composable
private fun MoodHeroBadge(state: MoodTeaserUiState) {
  val badgeColor = badgeColorFor(state)
  Box(
    modifier = Modifier
      .size(BadgeSize)
      .clip(CircleShape)
      .background(colors.glassSurface),
    contentAlignment = Alignment.Center
  ) {
    if (state.hasData) {
      Text(
        text = AverageRatingFormat.format(state.averageRating),
        style = WeatherVibeTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = badgeColor
      )
    } else {
      RatingDotsRow()
    }
  }
}

@Composable
private fun badgeColorFor(state: MoodTeaserUiState): Color = when {
  state.hasData -> ratingColor(state.averageRating.toInt().coerceAtLeast(1))
  else -> colors.accent.copy(alpha = FadedAccentAlpha)
}

@Composable
private fun RatingDotsRow() {
  Row(horizontalArrangement = Arrangement.spacedBy(DotSpacing)) {
    for (rating in RatingColors.MIN_RATING..RatingColors.MAX_RATING) {
      Box(
        modifier = Modifier
          .size(DotSize)
          .clip(CircleShape)
          .background(ratingColor(rating))
      )
    }
  }
}

@Composable
private fun heroGradient(): Brush = Brush.linearGradient(
  colors = listOf(
    colors.primaryContainer,
    colors.accent.copy(alpha = FadedAccentAlpha)
  )
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
        dayCount = 47,
        favoriteCondition = Condition.SUNNY
      ),
      onClick = {}
    )
  }
}
