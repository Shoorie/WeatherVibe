package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeDefaults.ViewAllMinHeight
import com.weather.vibe.feature.home.ui.HomeForecastTexts.weatherDetailsViewAll
import com.weather.vibe.feature.home.ui.HomeTextStyles.semiBold

@Composable
internal fun ViewAllDetailsLink(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {

  val label = weatherDetailsViewAll()
  val textStyle = semiBold(typography.bodyLarge)

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
      .padding(vertical = Small),
    horizontalArrangement = spacedBy(ExtraSmall, CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = textStyle,
      color = colors.accent
    )
    Icon(
      modifier = Modifier.size(IconSize.Small),
      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
      contentDescription = null,
      tint = colors.accent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ViewAllDetailsLink(
      modifier = Modifier.padding(Medium),
      onClick = {}
    )
  }
}
