package com.weather.vibe.feature.search.ui.component.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.preview.LocationItemPreview
import com.weather.vibe.feature.search.preview.params.LocationItemPreviewParams
import com.weather.vibe.feature.search.ui.SearchDefaults.LocationRowMinHeight
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartAddContentDescription
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartRemoveContentDescription
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartStateAdded
import com.weather.vibe.feature.search.ui.SearchResources.Texts.heartStateNotAdded
import com.weather.vibe.feature.search.ui.SearchTextStyles.locationNameStyle

@Composable
internal fun LocationRow(
  modifier: Modifier = Modifier,
  emoji: String,
  name: String,
  subtitle: String,
  showHeart: Boolean = false,
  isFavorite: Boolean = false,
  canToggleFavorite: Boolean = true,
  onHeartClick: () -> Unit = {},
  onClick: () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {}
      .clickable(role = Role.Button, onClick = onClick)
      .defaultMinSize(minHeight = LocationRowMinHeight)
      .padding(start = Medium, end = Small)
      .padding(vertical = Small),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Medium)
  ) {
    Text(
      text = emoji,
      style = typography.bodyLarge
    )
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(ExtraSmall)
    ) {
      Text(
        text = name,
        style = locationNameStyle(),
        color = colors.onBackground
      )
      if (subtitle.isNotEmpty()) {
        Text(
          text = subtitle,
          style = typography.bodySmall,
          color = colors.onSurfaceVariant
        )
      }
    }
    if (showHeart) {
      val description = when {
        isFavorite -> heartRemoveContentDescription()
        else -> heartAddContentDescription()
      }
      val stateLabel = when {
        isFavorite -> heartStateAdded()
        else -> heartStateNotAdded()
      }
      FavoriteHeartButton(
        isFavorite = isFavorite,
        enabled = canToggleFavorite,
        contentDescription = description,
        stateDescription = stateLabel,
        onClick = onHeartClick
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(LocationItemPreview::class)
  params: LocationItemPreviewParams
) {
  WeatherVibeTheme {
    LocationRow(
      emoji = params.emoji,
      name = params.name,
      subtitle = params.subtitle,
      showHeart = true,
      isFavorite = params.isFavorite,
      onClick = {}
    )
  }
}
