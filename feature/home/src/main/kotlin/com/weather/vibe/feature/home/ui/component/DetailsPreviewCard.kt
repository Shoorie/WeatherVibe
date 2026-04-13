package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.label.SectionLabel
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.DetailsPreviewCardPreview
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMetric
import com.weather.vibe.feature.home.ui.HomeDefaults.MetricGridColumns
import com.weather.vibe.feature.home.ui.HomeResources.Texts.weatherDetailsTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.weatherDetailsViewAll

private val ViewAllMinHeight = 48.dp

@Composable
internal fun DetailsPreviewCard(
  modifier: Modifier = Modifier,
  previewItems: List<MetricItemUiState>,
  onClick: () -> Unit
) {
  SectionLabel(
    modifier = modifier.fillMaxWidth(),
    text = weatherDetailsTitle(),
    uppercase = true
  ) {
    MetricGrid(items = previewItems)
    Spacer(modifier = Modifier.height(Padding.Small))
    ViewAllDetailsLink(onClick = onClick)
  }
}

@Composable
private fun ViewAllDetailsLink(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  val label = weatherDetailsViewAll()
  val baseStyle = typography.labelMedium
  val textStyle = remember(baseStyle) { baseStyle.copy(fontWeight = FontWeight.SemiBold) }
  Row(
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = ViewAllMinHeight)
      .clip(shapes.pill)
      .clickable(
        onClick = onClick,
        onClickLabel = label,
        role = Role.Button
      )
      .padding(vertical = Padding.Small),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = textStyle,
      color = colors.accent
    )
    Spacer(modifier = Modifier.size(Padding.ExtraSmall))
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
      contentDescription = null,
      tint = colors.accent,
      modifier = Modifier.size(IconSize.Small)
    )
  }
}

@Composable
private fun MetricGrid(
  modifier: Modifier = Modifier,
  items: List<MetricItemUiState>
) {
  val rowsOfTwo = remember(items) { items.chunked(MetricGridColumns) }
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(Padding.Small)
  ) {
    rowsOfTwo.forEach { row ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Padding.Small)
      ) {
        row.forEach { item ->
          MetricTile(modifier = Modifier.weight(1f), item = item)
        }
        if (row.size < MetricGridColumns) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

@Composable
private fun MetricTile(
  modifier: Modifier = Modifier,
  item: MetricItemUiState
) {
  val baseStyle = typography.titleMedium
  val valueStyle = remember(baseStyle) { baseStyle.copy(fontWeight = FontWeight.SemiBold) }
  Column(
    modifier = modifier
      .clip(shapes.cardSmall)
      .background(colors.surfaceVariant)
      .padding(Padding.Medium)
      .semantics(mergeDescendants = true) {}
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = item.label,
        style = typography.labelMedium,
        color = colors.onSurfaceVariant
      )
      Text(
        modifier = Modifier.clearAndSetSemantics {},
        text = item.icon,
        fontSize = EmojiMetric
      )
    }
    Spacer(modifier = Modifier.height(Padding.ExtraSmall))
    Text(
      text = item.value,
      style = valueStyle,
      color = colors.onBackground
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DetailsPreviewCardPreview::class)
  previewItems: List<MetricItemUiState>
) {
  WeatherVibeTheme {
    DetailsPreviewCard(
      modifier = Modifier.padding(Padding.Medium),
      previewItems = previewItems,
      onClick = {}
    )
  }
}
