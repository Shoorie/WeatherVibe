package com.weather.vibe.feature.locations.ui.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Navigation.MinTouchTarget
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke.Border
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareHintPickOne
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareHintPickZero
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.headerSubtitle
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.headerTitle
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.modeBrowse
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.modeCompare

@Composable
internal fun LocationsHeader(
  modifier: Modifier = Modifier,
  count: Int,
  compareMode: Boolean,
  selectedCount: Int,
  onToggleCompareMode: () -> Unit
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    HeaderLabels(
      modifier = Modifier.weight(1f),
      count = count,
      compareMode = compareMode,
      selectedCount = selectedCount
    )
    if (count >= LocationsDefaults.CompareMinCards) {
      CompareTogglePill(
        compareMode = compareMode,
        onClick = onToggleCompareMode
      )
    }
  }
}

@Composable
private fun HeaderLabels(
  modifier: Modifier = Modifier,
  count: Int,
  compareMode: Boolean,
  selectedCount: Int
) {
  Column(
    modifier = modifier.semantics(mergeDescendants = true) { heading() },
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = headerTitle(),
      style = typography.headlineMedium,
      color = colors.onBackground
    )
    Text(
      text = subtitleText(
        count = count,
        compareMode = compareMode,
        selectedCount = selectedCount
      ),
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      maxLines = 1,
      overflow = TextOverflow.Clip
    )
  }
}

@Composable
private fun subtitleText(
  count: Int,
  compareMode: Boolean,
  selectedCount: Int
): String = when {
  compareMode && selectedCount == 0 -> compareHintPickZero()
  compareMode && selectedCount == 1 -> compareHintPickOne()
  else -> headerSubtitle(count = count, limit = MAX_FAVORITES)
}

@Composable
private fun CompareTogglePill(
  compareMode: Boolean,
  onClick: () -> Unit
) {
  if (compareMode) CompareTogglePillFilled(onClick = onClick)
  else CompareTogglePillOutlined(onClick = onClick)
}

@Composable
private fun CompareTogglePillFilled(onClick: () -> Unit) {
  CompareTogglePillBase(
    label = modeBrowse(),
    leading = {
      Icon(
        modifier = Modifier.size(IconSize.Small),
        imageVector = Icons.Filled.Check,
        contentDescription = null,
        tint = colors.onAccent
      )
    },
    background = colors.accent,
    borderColor = Color.Transparent,
    contentColor = colors.onAccent,
    onClick = onClick
  )
}

@Composable
private fun CompareTogglePillOutlined(onClick: () -> Unit) {
  CompareTogglePillBase(
    label = modeCompare(),
    leading = null,
    background = Color.Transparent,
    borderColor = colors.accent,
    contentColor = colors.accent,
    onClick = onClick
  )
}

@Composable
private fun CompareTogglePillBase(
  label: String,
  leading: (@Composable () -> Unit)?,
  background: Color,
  borderColor: Color,
  contentColor: Color,
  onClick: (() -> Unit)?
) {
  Row(
    modifier = Modifier
      .defaultMinSize(minHeight = MinTouchTarget)
      .clip(shapes.pill)
      .background(background)
      .border(width = Border, color = borderColor, shape = shapes.pill)
      .let { if (onClick != null) it.clickable(onClick = onClick) else it }
      .semantics { role = Role.Button }
      .padding(horizontal = Medium, vertical = Small),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    leading?.invoke()
    Text(
      text = label,
      style = typography.labelMedium,
      color = contentColor
    )
  }
}


@PreviewLightDark
@Composable
private fun PreviewBrowse() {
  WeatherVibeTheme {
    LocationsHeader(
      count = 4,
      compareMode = false,
      selectedCount = 0,
      onToggleCompareMode = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewCompare() {
  WeatherVibeTheme {
    LocationsHeader(
      count = 4,
      compareMode = true,
      selectedCount = 1,
      onToggleCompareMode = {}
    )
  }
}
