package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.R
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Rated
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import kotlin.math.roundToInt

@Composable
internal fun RatingCard(
  modifier: Modifier = Modifier,
  state: RatingCardUiState,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit,
  onSharePosterClicked: () -> Unit
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = stringResource(R.string.vibe_rating_section_label).uppercase(),
      style = WeatherVibeTheme.typography.labelMedium,
      color = WeatherVibeTheme.colors.onSurfaceVariant,
      modifier = Modifier.padding(bottom = Padding.Small)
    )
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(CARD_RADIUS))
        .background(WeatherVibeTheme.colors.surfaceVariant)
        .border(
          width = 1.dp,
          color = WeatherVibeTheme.colors.outline,
          shape = RoundedCornerShape(CARD_RADIUS)
        )
        .padding(Padding.Medium)
    ) {
      when (state) {
        Loading -> RatingCardLoadingContent()
        is NotRated -> NotRatedContent(
          state = state,
          onSliderValueChanged = onSliderValueChanged,
          onSaveClicked = onSaveClicked,
          onViewHistoryClicked = onViewHistoryClicked
        )
        is Rated -> RatedContent(
          state = state,
          onEditClicked = onEditClicked,
          onViewHistoryClicked = onViewHistoryClicked,
          onSharePosterClicked = onSharePosterClicked
        )
      }
    }
  }
}

@Composable
private fun RatingCardLoadingContent() {
  Text(
    text = stringResource(R.string.vibe_rating_card_title),
    style = WeatherVibeTheme.typography.titleMedium,
    color = WeatherVibeTheme.colors.onSurface
  )
}

@Composable
private fun NotRatedContent(
  state: NotRated,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  val activeColor = ratingColor(state.sliderDraft)
  Text(
    text = stringResource(R.string.vibe_rating_card_title),
    style = WeatherVibeTheme.typography.titleMedium,
    color = WeatherVibeTheme.colors.onSurface
  )
  Spacer(Modifier.height(Padding.ExtraSmall))
  Text(
    text = stringResource(R.string.vibe_rating_card_subtitle),
    style = WeatherVibeTheme.typography.bodySmall,
    color = WeatherVibeTheme.colors.onSurfaceVariant
  )
  Spacer(Modifier.height(Padding.Medium))
  Row(verticalAlignment = Alignment.CenterVertically) {
    MoodFace(
      rating = state.sliderDraft,
      active = state.sliderTouched,
      size = MoodFaceDefaults.Size,
      contentDescription = moodFaceDescription(state.sliderDraft)
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = VibeRatingResources.scaleLabel(state.sliderDraft),
      style = WeatherVibeTheme.typography.titleMedium,
      color = if (state.sliderTouched) activeColor else WeatherVibeTheme.colors.onSurfaceVariant,
      fontWeight = FontWeight.SemiBold
    )
  }
  Spacer(Modifier.height(Padding.Small))
  Slider(
    value = state.sliderDraft.toFloat(),
    onValueChange = { onSliderValueChanged(it.roundToInt()) },
    valueRange = RATING_MIN_F..RATING_MAX_F,
    steps = SLIDER_STEPS,
    colors = SliderDefaults.colors(
      thumbColor = activeColor,
      activeTrackColor = activeColor,
      inactiveTrackColor = WeatherVibeTheme.colors.outline
    )
  )
  Spacer(Modifier.height(Padding.ExtraSmall))
  ScaleLabelsRow(selected = state.sliderDraft, activeColor = activeColor)
  Spacer(Modifier.height(Padding.Medium))
  Button(
    onClick = onSaveClicked,
    enabled = state.sliderTouched,
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(BUTTON_RADIUS),
    colors = ButtonDefaults.buttonColors(
      containerColor = WeatherVibeTheme.colors.accent,
      contentColor = WeatherVibeTheme.colors.onAccent
    )
  ) {
    Text(stringResource(R.string.vibe_rating_save))
  }
  TextButton(
    onClick = onViewHistoryClicked,
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = stringResource(R.string.vibe_rating_view_history),
      style = WeatherVibeTheme.typography.bodySmall,
      color = WeatherVibeTheme.colors.accent
    )
  }
}

@Composable
private fun ScaleLabelsRow(
  selected: Int,
  activeColor: androidx.compose.ui.graphics.Color
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    for (rating in RatingColors.MIN_RATING..RatingColors.MAX_RATING) {
      val isSelected = rating == selected
      Text(
        text = VibeRatingResources.scaleLabel(rating),
        style = WeatherVibeTheme.typography.labelSmall,
        color = if (isSelected) activeColor else WeatherVibeTheme.colors.onSurfaceVariant,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
      )
    }
  }
}

@Composable
private fun RatedContent(
  state: Rated,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit,
  onSharePosterClicked: () -> Unit
) {
  val ratingLabel = VibeRatingResources.scaleLabel(state.rating)
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    MoodFace(
      rating = state.rating,
      active = true,
      size = MoodFaceDefaults.SizeLarge,
      contentDescription = moodFaceDescription(state.rating)
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = stringResource(R.string.vibe_rating_rated_label, ratingLabel, state.rating),
      style = WeatherVibeTheme.typography.titleMedium,
      color = ratingColor(state.rating),
      fontWeight = FontWeight.Bold,
      modifier = Modifier.weight(1f)
    )
    TextButton(onClick = onEditClicked) {
      Text(
        text = stringResource(R.string.vibe_rating_change),
        style = WeatherVibeTheme.typography.labelMedium,
        color = WeatherVibeTheme.colors.onSurfaceVariant
      )
    }
  }
  Spacer(Modifier.height(Padding.Medium))
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    Button(
      onClick = onViewHistoryClicked,
      modifier = Modifier.weight(1f),
      shape = RoundedCornerShape(BUTTON_RADIUS),
      colors = ButtonDefaults.buttonColors(
        containerColor = WeatherVibeTheme.colors.accent,
        contentColor = WeatherVibeTheme.colors.onAccent
      )
    ) {
      Text(stringResource(R.string.vibe_rating_view_history))
    }
    OutlinedButton(
      onClick = onSharePosterClicked,
      modifier = Modifier.weight(1f),
      shape = RoundedCornerShape(BUTTON_RADIUS)
    ) {
      Text(stringResource(R.string.vibe_rating_share_poster))
    }
  }
}

@Composable
private fun moodFaceDescription(rating: Int): String {
  return stringResource(R.string.vibe_rating_mood_face_description, rating)
}

private val CARD_RADIUS = 20.dp
private val BUTTON_RADIUS = 12.dp
private const val RATING_MIN_F: Float = 1f
private const val RATING_MAX_F: Float = 5f
private const val SLIDER_STEPS: Int = 3

@PreviewLightDark
@Composable
private fun RatingCardNotRatedPreview() {
  WeatherVibeTheme {
    RatingCard(
      modifier = Modifier
        .background(WeatherVibeTheme.colors.backgroundGradientEnd)
        .padding(Padding.Medium),
      state = NotRated(sliderDraft = 4, sliderTouched = true),
      onSliderValueChanged = {},
      onSaveClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {},
      onSharePosterClicked = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun RatingCardRatedPreview() {
  WeatherVibeTheme {
    RatingCard(
      modifier = Modifier
        .background(WeatherVibeTheme.colors.backgroundGradientEnd)
        .padding(Padding.Medium),
      state = Rated(rating = 5),
      onSliderValueChanged = {},
      onSaveClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {},
      onSharePosterClicked = {}
    )
  }
}
