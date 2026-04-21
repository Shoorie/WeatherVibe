package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.animation.core.Transition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentDefaults.MinHeight
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentDefaults.Padding
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentDefaults.segmentTextStyle
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentDefaults.widthFractionFor
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.roundToInt

@Composable
fun <T> VibeSegmentedControl(
  modifier: Modifier = Modifier,
  segments: ImmutableList<VibeSegment<T>>,
  onSegmentClick: (T) -> Unit
) {

  val selection = rememberSelection(segments)

  Box(modifier = modifier.pillFrame()) {
    SlidingIndicator(
      selection = selection,
      segmentCount = segments.size
    )
    Row(modifier = Modifier.selectableGroup()) {
      segments.forEachIndexed { index, segment ->
        Segment(
          modifier = Modifier.weight(1f),
          segment = segment,
          index = index,
          selection = selection,
          onClick = rememberSegmentClick(segment.value, onSegmentClick)
        )
      }
    }
  }
}

@Composable
private fun BoxScope.SlidingIndicator(
  selection: Transition<Int>,
  segmentCount: Int
) {

  val position by rememberIndicatorPosition(selection)
  val widthPx = rememberIndicatorWidth()

  Box(
    modifier = Modifier
      .align(Alignment.CenterStart)
      .fillMaxWidth(widthFractionFor(segmentCount))
      .fillMaxHeight()
      .defaultMinSize(minHeight = MinHeight)
      .onSizeChanged { widthPx.intValue = it.width }
      .offset { IntOffset(x = indicatorOffset(position, widthPx.intValue), y = 0) }
      .clip(shapes.pill)
      .background(colors.accent)
  )
}

@Composable
private fun <T> Segment(
  modifier: Modifier = Modifier,
  segment: VibeSegment<T>,
  index: Int,
  selection: Transition<Int>,
  onClick: () -> Unit
) {

  val textColor by rememberSegmentTextColor(selection, index)

  Box(
    modifier = modifier
      .clip(shapes.pill)
      .selectable(
        selected = segment.isSelected,
        role = Role.Tab,
        onClick = onClick
      )
      .defaultMinSize(minHeight = MinHeight)
      .padding(horizontal = Small)
      .semantics { contentDescription = segment.contentDescription },
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = segment.label,
      color = textColor,
      style = segmentTextStyle(segment.isSelected)
    )
  }
}

@Composable
private fun Modifier.pillFrame(): Modifier =
  fillMaxWidth()
    .clip(shapes.pill)
    .background(colors.surfaceVariant)
    .border(Border, colors.outline, shapes.pill)
    .padding(Padding)

private fun indicatorOffset(position: Float, widthPx: Int): Int =
  (position * widthPx).roundToInt()

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(VibeSegmentedControlPreview::class)
  segments: ImmutableList<VibeSegment<String>>
) {
  WeatherVibeTheme {
    VibeSegmentedControl(
      segments = segments,
      onSegmentClick = {}
    )
  }
}
