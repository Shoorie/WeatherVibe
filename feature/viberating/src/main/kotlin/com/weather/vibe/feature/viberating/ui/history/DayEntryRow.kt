package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.history.state.DayEntryUiState
import com.weather.vibe.feature.viberating.preview.DayEntryRowPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.entryNoteQuote
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.entryRatingA11y
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.entryRatingLabel
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.entrySubtitle
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.entryTemperature
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.conditionEmoji
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.conditionLabel
import com.weather.vibe.feature.viberating.ui.history.DayEntryDefaults.BackgroundAlpha
import com.weather.vibe.feature.viberating.ui.history.DayEntryDefaults.BorderAlpha
import com.weather.vibe.feature.viberating.ui.history.DayEntryDefaults.BorderWidth
import com.weather.vibe.feature.viberating.ui.history.DayEntryDefaults.ConditionEmojiSize
import com.weather.vibe.feature.viberating.ui.history.DayEntryDefaults.ContentPadding

@Composable
internal fun DayEntryRow(
  modifier: Modifier = Modifier,
  entry: DayEntryUiState
) {
  val ratingTint = ratingColor(entry.rating)
  val resolvedConditionLabel = conditionLabel(entry.condition)
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.cardSmall)
      .background(ratingTint.copy(alpha = BackgroundAlpha))
      .border(
        width = BorderWidth,
        color = ratingTint.copy(alpha = BorderAlpha),
        shape = shapes.cardSmall
      )
      .padding(ContentPadding)
      .semantics(mergeDescendants = true) {
        contentDescription = ""
      }
  ) {
    EntryHeaderRow(
      entry = entry,
      conditionLabel = resolvedConditionLabel,
      ratingTint = ratingTint
    )
    if (entry.note != null) {
      Spacer(Modifier.height(Padding.Small))
      EntryNote(note = entry.note)
    }
  }
}

@Composable
private fun EntryHeaderRow(
  entry: DayEntryUiState,
  conditionLabel: String,
  ratingTint: Color
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    MoodFace(
      rating = entry.rating,
      active = true,
      contentDescription = entryRatingA11y(
        rating = entry.rating,
        conditionLabel = conditionLabel
      )
    )
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = entry.timeLabel,
        style = typography.titleSmall,
        color = colors.onSurface,
        fontWeight = FontWeight.SemiBold
      )
      Text(
        text = entrySubtitle(
          condition = conditionLabel,
          temperature = entryTemperature(temperatureC = entry.temperatureRounded)
        ),
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
    Text(
      text = conditionEmoji(entry.condition),
      fontSize = ConditionEmojiSize
    )
    Text(
      text = entryRatingLabel(rating = entry.rating),
      style = typography.titleSmall,
      color = ratingTint,
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
private fun EntryNote(note: String) {
  Text(
    text = entryNoteQuote(note = note),
    style = typography.bodyMedium,
    color = colors.onSurface
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DayEntryRowPreview::class)
  entry: DayEntryUiState
) {
  WeatherVibeTheme {
    DayEntryRow(entry = entry)
  }
}
