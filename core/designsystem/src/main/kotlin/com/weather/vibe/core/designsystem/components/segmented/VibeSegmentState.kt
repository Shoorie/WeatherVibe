package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentAnimates.IndicatorAnimationSpec
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentAnimates.TextColorAnimationSpec
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentDefaults.segmentTextColor
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun <T> rememberSelection(
  segments: ImmutableList<VibeSegment<T>>
): Transition<Int> {
  val selectedIndex = remember(segments) { selectedIndexOf(segments) }
  return updateTransition(
    targetState = selectedIndex,
    label = "segmentSelection"
  )
}

@Composable
internal fun rememberIndicatorPosition(selection: Transition<Int>): State<Float> =
  selection.animateFloat(
    transitionSpec = { IndicatorAnimationSpec },
    label = "segmentIndicatorPosition"
  ) { it.toFloat() }

@Composable
internal fun rememberIndicatorWidth(): MutableIntState =
  remember { mutableIntStateOf(0) }

@Composable
internal fun rememberSegmentTextColor(
  selection: Transition<Int>,
  index: Int
): State<Color> {

  val selected = segmentTextColor(selected = true)
  val unselected = segmentTextColor(selected = false)

  return selection.animateColor(
    transitionSpec = { TextColorAnimationSpec },
    label = "segmentTextColor"
  ) { target -> if (target == index) selected else unselected }
}

@Composable
internal fun <T> rememberSegmentClick(
  value: T,
  onSegmentClick: (T) -> Unit
): () -> Unit {
  val latest by rememberUpdatedState(onSegmentClick)
  return remember(value) { { latest(value) } }
}

private fun <T> selectedIndexOf(segments: ImmutableList<VibeSegment<T>>): Int =
  segments.indexOfFirst { it.isSelected }.coerceAtLeast(0)
