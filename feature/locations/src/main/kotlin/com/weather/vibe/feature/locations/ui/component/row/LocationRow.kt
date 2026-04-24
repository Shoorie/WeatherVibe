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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.zIndex
import com.weather.vibe.core.designsystem.components.pill.VibePill
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.ui.LocationsDefaults.DraggedAlpha
import com.weather.vibe.feature.locations.ui.LocationsDefaults.DraggedZIndex
import com.weather.vibe.feature.locations.ui.LocationsDefaults.LockedAlpha
import com.weather.vibe.feature.locations.ui.LocationsDefaults.RowEmojiSize
import com.weather.vibe.feature.locations.ui.LocationsDefaults.RowMinHeight
import com.weather.vibe.feature.locations.ui.LocationsDefaults.SelectionIndicatorSize
import com.weather.vibe.feature.locations.ui.LocationsEmojis
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.rowInfoFeels
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.temperature
import com.weather.vibe.feature.locations.ui.reorder.LocationsReorderState
import com.weather.vibe.feature.locations.ui.reorder.dragToReorder
import com.weather.vibe.feature.locations.ui.reorder.rememberLocationsReorderState
import com.weather.vibe.feature.locations.ui.reorder.reorderA11yActions

@Composable
internal fun LocationRow(
  modifier: Modifier = Modifier,
  card: LocationCardUiState,
  compareMode: Boolean,
  isSelected: Boolean,
  isLocked: Boolean,
  reorder: LocationsReorderState,
  onClick: () -> Unit,
  onRename: () -> Unit,
  onDelete: () -> Unit
) {
  val isDragged by remember(card.favoriteId, reorder) {
    derivedStateOf { reorder.isDragging(favoriteId = card.favoriteId) }
  }
  val reorderable = !compareMode && !isLocked
  Row(
    modifier = modifier
      .zIndex(if (isDragged) DraggedZIndex else 0f)
      .graphicsLayer {
        translationY = reorder.translationYFor(favoriteId = card.favoriteId)
        if (reorder.isDragging(favoriteId = card.favoriteId)) alpha = DraggedAlpha
      }
      .dragToReorder(
        favoriteId = card.favoriteId,
        reorder = reorder,
        enabled = reorderable
      )
      .reorderA11yActions(
        favoriteId = card.favoriteId,
        reorder = reorder,
        enabled = reorderable
      )
      .rowContainer(
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
      card = card
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
  val isHighlighted = compareMode && isSelected
  return this
    .fillMaxWidth()
    .alpha(if (isLocked) LockedAlpha else 1f)
    .clip(shapes.card)
    .background(rowBackgroundColor(isHighlighted = isHighlighted))
    .border(
      width = Border,
      color = rowBorderColor(isHighlighted = isHighlighted),
      shape = shapes.card
    )
    .let { if (isLocked) it else it.clickable(role = Button, onClick = onClick) }
    .padding(horizontal = Small, vertical = ExtraSmall)
    .defaultMinSize(minHeight = RowMinHeight)
}

@Composable
private fun WeatherEmoji(emoji: String) {
  Box(
    modifier = Modifier.size(RowEmojiSize),
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
  card: LocationCardUiState
) {
  Column(modifier = modifier) {
    NameRow(card = card)
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
private fun NameRow(card: LocationCardUiState) {
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
    LabelPill(card = card)
  }
}

@Composable
private fun LabelPill(card: LocationCardUiState) {
  val label = card.label ?: return
  VibePill(
    text = label,
    containerColor = labelPillContainerColor(seed = card.favoriteId),
    contentColor = colors.onAccent,
    style = typography.labelSmall
  )
}

@Composable
private fun TemperatureBlock(card: LocationCardUiState) {
  val temperature = card.temperature
  Column(horizontalAlignment = Alignment.End) {
    Text(
      text = temperature(value = temperature),
      style = typography.titleLarge,
      color = temperatureTextColor(hasValue = temperature != null),
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
      .size(SelectionIndicatorSize)
      .clip(CircleShape)
      .background(selectionDotColor(isSelected = isSelected))
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Box(modifier = Modifier.padding(Small)) {
      LocationRow(
        card = LocationsPreviewData.london,
        compareMode = false,
        isSelected = false,
        isLocked = false,
        reorder = rememberLocationsReorderState(
          listState = rememberLazyListState(),
          cards = emptyList(),
          onCommit = {}
        ),
        onClick = {},
        onRename = {},
        onDelete = {}
      )
    }
  }
}
