package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.R
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Loading
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Rated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import kotlin.math.roundToInt

@Composable
internal fun RatingCard(
  modifier: Modifier = Modifier,
  state: RatingCardUiState,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
  onRetryClicked: () -> Unit,
  onDismissErrorClicked: () -> Unit,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = stringResource(R.string.vibe_rating_section_label).uppercase(),
      style = WeatherVibeTheme.typography.labelMedium,
      color = WeatherVibeTheme.colors.onSurfaceVariant,
      modifier = Modifier
        .padding(bottom = Padding.Small)
        .semantics { heading() }
    )
    VibeCard(
      shape = shapes.card,
      containerColor = WeatherVibeTheme.colors.glassSurface,
      contentPadding = Padding.Medium
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        when (state) {
          Loading -> RatingCardLoadingContent()
          is NotRated -> DraftContent(
            draft = state.sliderDraft,
            touched = state.sliderTouched,
            saving = false,
            onSliderValueChanged = onSliderValueChanged,
            onSaveClicked = onSaveClicked
          )
          is Saving -> DraftContent(
            draft = state.sliderDraft,
            touched = true,
            saving = true,
            onSliderValueChanged = {},
            onSaveClicked = {}
          )
          is SaveError -> SaveErrorContent(
            draft = state.sliderDraft,
            onRetryClicked = onRetryClicked,
            onDismissErrorClicked = onDismissErrorClicked
          )
          is Rated -> RatedContent(
            rating = state.rating,
            onEditClicked = onEditClicked,
            onViewHistoryClicked = onViewHistoryClicked
          )
        }
      }
    }
    if (state is NotRated || state is Saving) {
      TextButton(
        onClick = onViewHistoryClicked,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = Padding.ExtraSmall)
      ) {
        Text(
          text = stringResource(R.string.vibe_rating_view_history_link),
          style = WeatherVibeTheme.typography.bodySmall,
          color = WeatherVibeTheme.colors.accent
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
private fun DraftContent(
  draft: Int,
  touched: Boolean,
  saving: Boolean,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit
) {
  val activeColor = ratingColor(draft)
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
      rating = draft,
      active = touched,
      size = MoodFaceDefaults.Size,
      contentDescription = stringResource(R.string.vibe_rating_mood_face_description, draft)
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = VibeRatingResources.scaleLabel(draft),
      style = WeatherVibeTheme.typography.titleMedium,
      color = if (touched) activeColor else WeatherVibeTheme.colors.onSurfaceVariant,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
    )
  }
  Spacer(Modifier.height(Padding.Small))
  HapticSlider(
    draft = draft,
    enabled = !saving,
    activeColor = activeColor,
    onValueChanged = onSliderValueChanged
  )
  Spacer(Modifier.height(Padding.ExtraSmall))
  ScaleLabelsRow(selected = draft, activeColor = activeColor)
  Spacer(Modifier.height(Padding.Medium))
  Button(
    onClick = onSaveClicked,
    enabled = touched && !saving,
    modifier = Modifier.fillMaxWidth(),
    shape = shapes.cardSmall,
    colors = ButtonDefaults.buttonColors(
      containerColor = WeatherVibeTheme.colors.accent,
      contentColor = WeatherVibeTheme.colors.onAccent
    )
  ) {
    if (saving) {
      CircularProgressIndicator(
        modifier = Modifier.size(SpinnerSize),
        color = WeatherVibeTheme.colors.onAccent,
        strokeWidth = 2.dp
      )
      Spacer(Modifier.width(Padding.Small))
      Text(stringResource(R.string.vibe_rating_saving))
    } else {
      Text(stringResource(R.string.vibe_rating_save))
    }
  }
}

@Composable
private fun HapticSlider(
  draft: Int,
  enabled: Boolean,
  activeColor: Color,
  onValueChanged: (Int) -> Unit
) {
  val haptics = LocalHapticFeedback.current
  val sliderStateDescription = VibeRatingResources.scaleLabel(draft)
  val sliderContentDescription = stringResource(R.string.vibe_rating_slider_description)
  LaunchedEffect(draft) {
    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
  }
  Slider(
    value = draft.toFloat(),
    onValueChange = { newValue ->
      val rounded = newValue.roundToInt().coerceIn(RatingColors.MIN_RATING, RatingColors.MAX_RATING)
      if (rounded != draft) onValueChanged(rounded)
    },
    valueRange = RatingColors.MIN_RATING.toFloat()..RatingColors.MAX_RATING.toFloat(),
    enabled = enabled,
    colors = SliderDefaults.colors(
      thumbColor = activeColor,
      activeTrackColor = activeColor,
      inactiveTrackColor = WeatherVibeTheme.colors.outline
    ),
    modifier = Modifier.semantics {
      contentDescription = sliderContentDescription
      stateDescription = sliderStateDescription
    }
  )
}

@Composable
private fun ScaleLabelsRow(
  selected: Int,
  activeColor: Color
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
private fun SaveErrorContent(
  draft: Int,
  onRetryClicked: () -> Unit,
  onDismissErrorClicked: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) { liveRegion = LiveRegionMode.Assertive }
  ) {
    Text(
      text = stringResource(R.string.vibe_rating_save_error_title),
      style = WeatherVibeTheme.typography.titleMedium,
      color = WeatherVibeTheme.colors.error,
      fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(Padding.ExtraSmall))
    Text(
      text = stringResource(R.string.vibe_rating_save_error_body),
      style = WeatherVibeTheme.typography.bodySmall,
      color = WeatherVibeTheme.colors.onSurfaceVariant
    )
    Spacer(Modifier.height(Padding.ExtraSmall))
    Text(
      text = "${VibeRatingResources.scaleLabel(draft)} · $draft/5",
      style = WeatherVibeTheme.typography.bodyMedium,
      color = ratingColor(draft),
      fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(Padding.Medium))
    Row(horizontalArrangement = Arrangement.spacedBy(Padding.Small)) {
      Button(
        onClick = onRetryClicked,
        modifier = Modifier.weight(1f),
        shape = shapes.cardSmall,
        colors = ButtonDefaults.buttonColors(
          containerColor = WeatherVibeTheme.colors.accent,
          contentColor = WeatherVibeTheme.colors.onAccent
        )
      ) {
        Text(stringResource(R.string.vibe_rating_retry))
      }
      OutlinedButton(
        onClick = onDismissErrorClicked,
        modifier = Modifier.weight(1f),
        shape = shapes.cardSmall
      ) {
        Text(stringResource(R.string.vibe_rating_dismiss_error))
      }
    }
  }
}

@Composable
private fun RatedContent(
  rating: Int,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  val ratingLabel = VibeRatingResources.scaleLabel(rating)
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {},
    verticalAlignment = Alignment.CenterVertically
  ) {
    MoodFace(
      rating = rating,
      active = true,
      size = MoodFaceDefaults.SizeLarge
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = stringResource(R.string.vibe_rating_rated_label, ratingLabel, rating),
      style = WeatherVibeTheme.typography.titleMedium,
      color = ratingColor(rating),
      fontWeight = FontWeight.Bold,
      modifier = Modifier.weight(1f)
    )
    TextButton(
      onClick = onEditClicked,
      modifier = Modifier.defaultMinSize(minWidth = TouchTarget, minHeight = TouchTarget)
    ) {
      Text(
        text = stringResource(R.string.vibe_rating_change),
        style = WeatherVibeTheme.typography.labelMedium,
        color = WeatherVibeTheme.colors.onSurfaceVariant
      )
    }
  }
  Spacer(Modifier.height(Padding.Medium))
  Button(
    onClick = onViewHistoryClicked,
    modifier = Modifier.fillMaxWidth(),
    shape = shapes.cardSmall,
    colors = ButtonDefaults.buttonColors(
      containerColor = WeatherVibeTheme.colors.accent,
      contentColor = WeatherVibeTheme.colors.onAccent
    )
  ) {
    Icon(
      imageVector = Icons.Filled.DateRange,
      contentDescription = null,
      modifier = Modifier.size(ButtonIconSize)
    )
    Spacer(Modifier.width(Padding.Small))
    Text(stringResource(R.string.vibe_rating_view_history))
  }
}

private val SpinnerSize = 16.dp
private val TouchTarget = 48.dp
private val ButtonIconSize = 18.dp

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
      onRetryClicked = {},
      onDismissErrorClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun RatingCardSavingPreview() {
  WeatherVibeTheme {
    RatingCard(
      modifier = Modifier
        .background(WeatherVibeTheme.colors.backgroundGradientEnd)
        .padding(Padding.Medium),
      state = Saving(sliderDraft = 4),
      onSliderValueChanged = {},
      onSaveClicked = {},
      onRetryClicked = {},
      onDismissErrorClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun RatingCardErrorPreview() {
  WeatherVibeTheme {
    RatingCard(
      modifier = Modifier
        .background(WeatherVibeTheme.colors.backgroundGradientEnd)
        .padding(Padding.Medium),
      state = SaveError(sliderDraft = 3),
      onSliderValueChanged = {},
      onSaveClicked = {},
      onRetryClicked = {},
      onDismissErrorClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {}
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
      onRetryClicked = {},
      onDismissErrorClicked = {},
      onEditClicked = {},
      onViewHistoryClicked = {}
    )
  }
}
