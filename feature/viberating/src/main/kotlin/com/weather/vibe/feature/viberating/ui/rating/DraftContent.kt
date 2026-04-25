package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingFormDraftUiState
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.SpinnerSize

@Composable
internal fun DraftContent(
  draft: RatingFormDraftUiState,
  todayEntryCount: Int,
  saving: Boolean,
  onSliderValueChanged: (Int) -> Unit,
  onNoteValueChanged: (String) -> Unit,
  onNoteExpandClick: () -> Unit,
  onNoteCollapseClick: () -> Unit,
  onSaveClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  val activeColor = ratingColor(draft.sliderValue)
  DraftHeader(todayEntryCount = todayEntryCount)
  Spacer(Modifier.height(Padding.Small))
  DraftMoodRow(
    rating = draft.sliderValue,
    touched = draft.sliderTouched,
    activeColor = activeColor
  )
  Spacer(Modifier.height(Padding.ExtraSmall))
  HapticSlider(
    draft = draft.sliderValue,
    enabled = !saving,
    activeColor = activeColor,
    onValueChanged = onSliderValueChanged
  )
  ScaleLabelsRow(selected = draft.sliderValue, activeColor = activeColor)
  Spacer(Modifier.height(Padding.Small))
  NoteEditor(
    expanded = draft.noteExpanded,
    enabled = !saving,
    note = draft.note,
    onExpandClick = onNoteExpandClick,
    onCollapseClick = onNoteCollapseClick,
    onValueChange = onNoteValueChanged
  )
  Spacer(Modifier.height(Padding.Small))
  SaveButton(
    saving = saving,
    enabled = draft.sliderTouched && !saving,
    onClick = onSaveClicked
  )
  Spacer(Modifier.height(Padding.ExtraSmall))
  ViewHistoryPillRow(onClick = onViewHistoryClicked)
}

@Composable
private fun DraftHeader(todayEntryCount: Int) {
  Text(
    text = Texts.cardTitle(),
    style = typography.titleMedium,
    color = colors.onSurface
  )
  if (todayEntryCount > 0) {
    Spacer(Modifier.height(Padding.ExtraSmall))
    TodayEntriesBadge(count = todayEntryCount)
  }
}

@Composable
private fun DraftMoodRow(
  rating: Int,
  touched: Boolean,
  activeColor: Color
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    MoodFace(
      rating = rating,
      active = touched,
      contentDescription = Texts.moodFaceDescription(rating)
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = VibeRatingResources.scaleLabel(rating),
      style = typography.titleMedium,
      color = if (touched) activeColor else colors.onSurfaceVariant,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
    )
  }
}

@Composable
private fun SaveButton(
  saving: Boolean,
  enabled: Boolean,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    enabled = enabled,
    modifier = Modifier.fillMaxWidth(),
    shape = shapes.cardSmall,
    colors = ButtonDefaults.buttonColors(
      containerColor = colors.accent,
      contentColor = colors.onAccent
    )
  ) {
    if (saving) {
      CircularProgressIndicator(
        modifier = Modifier.size(SpinnerSize),
        color = colors.onAccent,
        strokeWidth = SaveSpinnerStroke
      )
      Spacer(Modifier.width(Padding.Small))
      Text(Texts.saving())
    } else {
      Text(Texts.save())
    }
  }
}

private val SaveSpinnerStroke = 2.dp
