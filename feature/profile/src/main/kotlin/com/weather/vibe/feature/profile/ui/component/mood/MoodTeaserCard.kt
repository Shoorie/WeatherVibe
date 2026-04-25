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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.domain.weather.model.Condition.SUNNY
import com.weather.vibe.feature.profile.presentation.MoodBadgeStyle.Faded
import com.weather.vibe.feature.profile.presentation.MoodBadgeStyle.Rating
import com.weather.vibe.feature.profile.presentation.MoodSummary
import com.weather.vibe.feature.profile.presentation.MoodSummary.Available
import com.weather.vibe.feature.profile.presentation.MoodTeaserUiState
import com.weather.vibe.feature.profile.presentation.MoodTeaserUiState.Companion.EMPTY
import com.weather.vibe.feature.profile.presentation.MoodTeaserViewModel
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodBodyEmpty
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodCta
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodDaysPlural
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodSummary
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodTitle
import com.weather.vibe.feature.profile.ui.ProfileTextStyles.rowBody
import com.weather.vibe.feature.profile.ui.ProfileTextStyles.sectionTitle
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.AverageRatingFormat
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.BadgeSize
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.DotSize
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.DotSpacing
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserDefaults.EmptyBodyAlpha
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
      Modifier.clickable(
        role = Role.Button,
        onClickLabel = clickLabel,
        onClick = onClick
      )
    } else Modifier
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
            modifier = Modifier.semantics { heading() },
            text = moodTitle(),
            style = sectionTitle(),
            color = colors.onPrimaryContainer,
            fontWeight = Bold
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
  when (val summary = state.summary) {
    is Available -> AvailableSummaryText(summary = summary)
    MoodSummary.Empty -> EmptySummaryText()
  }
}

@Composable
private fun AvailableSummaryText(summary: Available) {
  Text(
    text = moodSummary(
      average = summary.averageFormatted,
      dayCount = summary.dayCount,
      daysLabel = moodDaysPlural(count = summary.dayCount)
    ),
    style = rowBody(),
    color = colors.onPrimaryContainer,
    fontWeight = FontWeight.SemiBold
  )
}

@Composable
private fun EmptySummaryText() {
  Text(
    text = moodBodyEmpty(),
    style = rowBody(),
    color = colors.onPrimaryContainer.copy(alpha = EmptyBodyAlpha)
  )
}

@Composable
private fun MoodHeroBadge(state: MoodTeaserUiState) {
  Box(
    modifier = Modifier
      .size(BadgeSize)
      .clip(CircleShape)
      .background(colors.glassSurface),
    contentAlignment = Alignment.Center
  ) {
    when (val style = state.badgeStyle) {
      is Rating -> AverageRatingText(
        value = state.averageRating,
        ratingForColor = style.rating
      )
      Faded -> RatingDotsRow()
    }
  }
}

@Composable
private fun AverageRatingText(value: Double, ratingForColor: Int) {
  Text(
    text = AverageRatingFormat.format(value),
    style = typography.titleLarge,
    fontWeight = Bold,
    color = ratingColor(ratingForColor)
  )
}

@Composable
private fun RatingDotsRow() {
  Row(horizontalArrangement = Arrangement.spacedBy(DotSpacing)) {
    for (rating in MIN_RATING..MAX_RATING) {
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

@PreviewLightDark
@Composable
private fun PreviewEmpty() {
  WeatherVibeTheme {
    MoodTeaserCardContent(
      state = EMPTY,
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
        favoriteCondition = SUNNY
      ),
      onClick = {}
    )
  }
}
