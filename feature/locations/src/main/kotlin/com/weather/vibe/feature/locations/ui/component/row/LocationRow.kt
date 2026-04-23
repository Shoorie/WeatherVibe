package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.pill.VibePill
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsEmojis
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.rowInfoFeels
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.rowTemperature

@Composable
internal fun LocationRow(
  modifier: Modifier = Modifier,
  card: LocationCardUiState,
  positionIndex: Int,
  compareMode: Boolean,
  isSelected: Boolean,
  isLocked: Boolean,
  onClick: () -> Unit,
  onRename: () -> Unit,
  onDelete: () -> Unit
) {
  Row(
    modifier = modifier.rowContainer(
      compareMode = compareMode,
      isSelected = isSelected,
      isLocked = isLocked,
      onClick = onClick
    ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    WeatherEmoji(emoji = card.weather?.emoji ?: LocationsEmojis.fallback())
    LocationLabels(
      modifier = Modifier.weight(1f),
      card = card,
      positionIndex = positionIndex
    )
    TemperatureBlock(card = card)
    TrailingAffordance(
      compareMode = compareMode,
      isSelected = isSelected,
      onRename = onRename,
      onDelete = onDelete
    )
  }
}

@Composable
private fun Modifier.rowContainer(
  compareMode: Boolean,
  isSelected: Boolean,
  isLocked: Boolean,
  onClick: () -> Unit
): Modifier {
  val background = if (compareMode && isSelected) colors.primaryContainer else colors.glassSurface
  val borderColor = if (compareMode && isSelected) colors.accent else colors.outlineVariant
  return this
    .fillMaxWidth()
    .alpha(if (isLocked) LocationsDefaults.LockedAlpha else 1f)
    .clip(shapes.card)
    .background(background)
    .border(
      width = Border,
      color = borderColor,
      shape = shapes.card
    )
    .let { if (isLocked) it else it.clickable(role = Role.Button, onClick = onClick) }
    .padding(horizontal = Small, vertical = ExtraSmall)
    .defaultMinSize(minHeight = LocationsDefaults.RowMinHeight)
}

@Composable
private fun WeatherEmoji(emoji: String) {
  Box(
    modifier = Modifier.size(LocationsDefaults.RowEmojiSize),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = emoji,
      style = typography.titleLarge
    )
  }
}

@Composable
private fun LocationLabels(
  modifier: Modifier = Modifier,
  card: LocationCardUiState,
  positionIndex: Int
) {
  Column(modifier = modifier) {
    NameRow(card = card, positionIndex = positionIndex)
    Text(
      text = card.region,
      style = typography.bodySmall,
      color = colors.onSurfaceVariant,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}

@Composable
private fun NameRow(
  card: LocationCardUiState,
  positionIndex: Int
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      modifier = Modifier.weight(weight = 1f, fill = false),
      text = card.name,
      style = typography.titleMedium,
      color = colors.onBackground,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
    LabelPill(card = card, positionIndex = positionIndex)
  }
}

@Composable
private fun LabelPill(
  card: LocationCardUiState,
  positionIndex: Int
) {
  val label = card.label ?: return
  VibePill(
    text = label,
    containerColor = labelPillContainerColor(index = positionIndex),
    contentColor = colors.onAccent,
    style = typography.labelSmall
  )
}

@Composable
private fun TemperatureBlock(card: LocationCardUiState) {
  val temperature = card.temperature
  Column(horizontalAlignment = Alignment.End) {
    Text(
      text = temperature?.let { rowTemperature(value = it) } ?: LocationsDefaults.TemperaturePlaceholder,
      style = typography.titleLarge,
      color = if (temperature == null) colors.textTertiary else colors.onBackground,
      textAlign = TextAlign.End
    )
    card.feelsLike?.let { feels ->
      Text(
        text = rowInfoFeels(value = feels),
        style = typography.labelSmall,
        color = colors.textTertiary,
        maxLines = 1
      )
    }
  }
}

@Composable
private fun TrailingAffordance(
  compareMode: Boolean,
  isSelected: Boolean,
  onRename: () -> Unit,
  onDelete: () -> Unit
) {
  if (compareMode) {
    SelectionDot(isSelected = isSelected)
    return
  }
  LocationRowMenu(
    onRename = onRename,
    onDelete = onDelete
  )
}

@Composable
private fun SelectionDot(isSelected: Boolean) {
  Box(
    modifier = Modifier
      .size(LocationsDefaults.SelectionIndicatorSize)
      .clip(CircleShape)
      .background(if (isSelected) colors.accent else colors.outlineVariant)
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Box(modifier = Modifier.padding(Small)) {
      LocationRow(
        card = LocationsPreviewData.warsaw,
        positionIndex = 0,
        compareMode = false,
        isSelected = false,
        isLocked = false,
        onClick = {},
        onRename = {},
        onDelete = {}
      )
    }
  }
}
