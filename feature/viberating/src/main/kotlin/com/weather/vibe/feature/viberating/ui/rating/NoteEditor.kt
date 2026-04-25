package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.presentation.rating.RatingCardStateFactory
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.NoteFadeMs
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.NoteFieldMinHeight
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.NoteIconSize
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.NoteMaxLines
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.TouchTarget

@Composable
internal fun NoteEditor(
  expanded: Boolean,
  enabled: Boolean,
  note: String,
  onExpandClick: () -> Unit,
  onCollapseClick: () -> Unit,
  onValueChange: (String) -> Unit
) {
  AnimatedContent(
    targetState = expanded,
    transitionSpec = {
      fadeIn(tween(NoteFadeMs)) togetherWith fadeOut(tween(NoteFadeMs))
    },
    label = "note-editor"
  ) { isExpanded ->
    if (isExpanded) {
      NoteForm(
        enabled = enabled,
        note = note,
        onCollapseClick = onCollapseClick,
        onValueChange = onValueChange
      )
    } else {
      AddNoteButton(enabled = enabled, onClick = onExpandClick)
    }
  }
}

@Composable
private fun AddNoteButton(
  enabled: Boolean,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center
  ) {
    TextButton(
      onClick = onClick,
      enabled = enabled,
      modifier = Modifier.defaultMinSize(minHeight = TouchTarget),
      shape = shapes.pill,
      contentPadding = PaddingValues(horizontal = Padding.Medium, vertical = Padding.ExtraSmall)
    ) {
      Icon(
        imageVector = Icons.Filled.Edit,
        contentDescription = null,
        modifier = Modifier.size(NoteIconSize),
        tint = colors.onSurfaceVariant
      )
      Spacer(Modifier.width(Padding.Small))
      Text(
        text = Texts.noteAdd(),
        style = typography.labelMedium,
        color = colors.onSurfaceVariant,
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@Composable
private fun NoteForm(
  enabled: Boolean,
  note: String,
  onCollapseClick: () -> Unit,
  onValueChange: (String) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    NoteFormHeader(enabled = enabled, onCollapseClick = onCollapseClick)
    Spacer(Modifier.height(Padding.ExtraSmall))
    TextField(
      value = note,
      onValueChange = { onValueChange(it.take(RatingCardStateFactory.NOTE_MAX_LENGTH)) },
      modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = NoteFieldMinHeight),
      enabled = enabled,
      placeholder = { Text(Texts.notePlaceholder(), style = typography.bodyMedium) },
      textStyle = typography.bodyMedium,
      shape = shapes.cardSmall,
      colors = noteTextFieldColors(),
      maxLines = NoteMaxLines
    )
    Spacer(Modifier.height(Padding.ExtraSmall))
    NoteCharacterCounter(currentLength = note.length)
  }
}

@Composable
private fun noteTextFieldColors() = TextFieldDefaults.colors(
  focusedContainerColor = colors.glassSurface,
  unfocusedContainerColor = colors.glassSurface,
  disabledContainerColor = colors.glassSurface,
  focusedIndicatorColor = Color.Transparent,
  unfocusedIndicatorColor = Color.Transparent,
  disabledIndicatorColor = Color.Transparent,
  errorIndicatorColor = Color.Transparent,
  focusedTextColor = colors.onSurface,
  unfocusedTextColor = colors.onSurface,
  cursorColor = colors.accent,
  focusedPlaceholderColor = colors.onSurfaceVariant,
  unfocusedPlaceholderColor = colors.onSurfaceVariant
)

@Composable
private fun NoteFormHeader(
  enabled: Boolean,
  onCollapseClick: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = Texts.noteLabel(),
      style = typography.labelMedium,
      color = colors.onSurface,
      fontWeight = FontWeight.SemiBold
    )
    IconButton(
      onClick = onCollapseClick,
      enabled = enabled
    ) {
      Icon(
        imageVector = Icons.Filled.Close,
        contentDescription = Texts.noteCollapse(),
        tint = colors.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun NoteCharacterCounter(currentLength: Int) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End
  ) {

    Text(
      text = Texts.noteCounter(
        current = currentLength,
        max = RatingCardStateFactory.NOTE_MAX_LENGTH
      ),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
  }
}
