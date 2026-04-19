package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedDefaults.MinHeight
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedDefaults.Padding
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedDefaults.segmentBackgroundColor
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedDefaults.segmentTextColor
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedDefaults.segmentTextStyle
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun <T> VibeSegmentedControl(
  modifier: Modifier = Modifier,
  segments: ImmutableList<VibeSegment<T>>,
  onSegmentClick: (T) -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.pill)
      .background(colors.surfaceVariant)
      .border(Border, colors.outline, shapes.pill)
      .padding(Padding)
      .selectableGroup()
  ) {
    segments.forEach { segment ->
      Segment(
        modifier = Modifier.weight(1f),
        label = segment.label,
        a11yLabel = segment.contentDescription,
        isSelected = segment.isSelected,
        onClick = { onSegmentClick(segment.value) }
      )
    }
  }
}

@Composable
private fun Segment(
  modifier: Modifier = Modifier,
  label: String,
  a11yLabel: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .clip(shapes.pill)
      .background(segmentBackgroundColor(isSelected))
      .selectable(
        selected = isSelected,
        role = Role.Tab,
        onClick = onClick
      )
      .defaultMinSize(minHeight = MinHeight)
      .padding(horizontal = Small)
      .semantics { contentDescription = a11yLabel },
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = label,
      color = segmentTextColor(isSelected),
      style = segmentTextStyle(isSelected)
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeSegmentedControl(
      segments = persistentListOf(
        VibeSegment(
          value = "c",
          label = "°C",
          contentDescription = "Celsius",
          isSelected = true
        ),
        VibeSegment(
          value = "f",
          label = "°F",
          contentDescription = "Fahrenheit",
          isSelected = false
        )
      ),
      onSegmentClick = {}
    )
  }
}
