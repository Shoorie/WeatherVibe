package com.weather.vibe.feature.profile.ui.component.appearance

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.feature.profile.preview.AppearanceRowPreviewProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileAppearanceRowUiState
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentBackgroundAlpha
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentChipShape
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentFirstIndex
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentIndicatorLabel
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentInnerPadding
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentMinHeight
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentMinSegmentCount
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentShape
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentTransitionLabel
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentTransitionMs
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentZeroOffset
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AppearanceSegmentZeroWidth
import kotlin.math.roundToInt

@Composable
internal fun ThreeStateSegment(
  modifier: Modifier = Modifier,
  state: ProfileAppearanceRowUiState,
  onSelect: (ThemeMode) -> Unit
) {

  val accent = colors.accent
  val backgroundColor = remember(accent) { accent.copy(alpha = AppearanceSegmentBackgroundAlpha) }
  val selectedIndex = remember(state) {
    state.options
      .indexOfFirst { it.isSelected }
      .coerceAtLeast(AppearanceSegmentFirstIndex)
  }
  val selection = updateTransition(
    targetState = selectedIndex,
    label = AppearanceSegmentTransitionLabel
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(AppearanceSegmentShape)
      .background(backgroundColor)
      .padding(AppearanceSegmentInnerPadding)
  ) {
    SlidingIndicator(
      selection = selection,
      segmentCount = state.options.size
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .selectableGroup()
    ) {
      state.options.forEachIndexed { index, option ->
        ThreeStateSegmentChip(
          modifier = Modifier.weight(1f),
          index = index,
          option = option,
          selection = selection,
          onClick = remember(option.mode, onSelect) { { onSelect(option.mode) } }
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

  val widthPx = remember { mutableIntStateOf(AppearanceSegmentZeroWidth) }
  val position by selection.animateFloat(
    transitionSpec = { tween(durationMillis = AppearanceSegmentTransitionMs) },
    label = AppearanceSegmentIndicatorLabel
  ) { it.toFloat() }

  Box(
    modifier = Modifier
      .align(Alignment.CenterStart)
      .fillMaxWidth(fillMaxWidthFraction(segmentCount))
      .fillMaxHeight()
      .defaultMinSize(minHeight = AppearanceSegmentMinHeight)
      .onSizeChanged(rememberSizeRecorder(widthPx))
      .offset { IntOffset(x = (position * widthPx.intValue).roundToInt(), y = AppearanceSegmentZeroOffset) }
      .clip(AppearanceSegmentChipShape)
      .background(colors.accent)
  )
}

private fun rememberSizeRecorder(widthPx: MutableIntState): (IntSize) -> Unit =
  { size -> widthPx.intValue = size.width }

private fun fillMaxWidthFraction(segmentCount: Int): Float =
  1f / segmentCount.coerceAtLeast(AppearanceSegmentMinSegmentCount)

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(AppearanceRowPreviewProvider::class)
  state: ProfileAppearanceRowUiState
) {
  WeatherVibeTheme {
    ThreeStateSegment(
      state = state,
      onSelect = {}
    )
  }
}
