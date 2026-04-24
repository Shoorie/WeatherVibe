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
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.SpinnerSize

@Composable
internal fun DraftContent(
  draft: Int,
  touched: Boolean,
  saving: Boolean,
  onSliderValueChanged: (Int) -> Unit,
  onSaveClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  val activeColor = ratingColor(draft)
  Text(
    text = Texts.cardTitle(),
    style = typography.titleMedium,
    color = colors.onSurface
  )
  Spacer(Modifier.height(Padding.ExtraSmall))
  Text(
    text = Texts.cardSubtitle(),
    style = typography.bodySmall,
    color = colors.onSurfaceVariant
  )
  Spacer(Modifier.height(Padding.Medium))
  DraftMoodRow(draft = draft, touched = touched, activeColor = activeColor)
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
  SaveButton(saving = saving, enabled = touched && !saving, onClick = onSaveClicked)
  Spacer(Modifier.height(Padding.Small))
  ViewHistoryPillRow(onClick = onViewHistoryClicked)
}

@Composable
private fun DraftMoodRow(
  draft: Int,
  touched: Boolean,
  activeColor: androidx.compose.ui.graphics.Color
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    MoodFace(
      rating = draft,
      active = touched,
      size = MoodFaceDefaults.Size,
      contentDescription = Texts.moodFaceDescription(draft)
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = VibeRatingResources.scaleLabel(draft),
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
        strokeWidth = 2.dp
      )
      Spacer(Modifier.width(Padding.Small))
      Text(Texts.saving())
    } else {
      Text(Texts.save())
    }
  }
}
