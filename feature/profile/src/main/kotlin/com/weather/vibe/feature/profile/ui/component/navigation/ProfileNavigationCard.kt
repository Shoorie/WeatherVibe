package com.weather.vibe.feature.profile.ui.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.ui.ProfileDefaults.NavIconContainerSize
import com.weather.vibe.feature.profile.ui.ProfileDefaults.NavIconCornerRadius
import com.weather.vibe.feature.profile.ui.ProfileDefaults.NavIconSize
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun ProfileNavigationCard(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  title: String,
  body: String,
  onClick: () -> Unit
) {
  VibeCard(
    modifier = modifier,
    contentPadding = Medium,
    onClick = onClick,
    onClickLabel = title
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(Medium)
    ) {
      LeadingIcon(icon = icon)
      TitleAndBody(
        modifier = Modifier.weight(1f),
        title = title,
        body = body
      )
      Icon(
        modifier = Modifier.size(IconSize.Small),
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = colors.onPrimaryContainer
      )
    }
  }
}

@Composable
private fun LeadingIcon(icon: ImageVector) {
  Box(
    modifier = Modifier
      .size(NavIconContainerSize)
      .clip(RoundedCornerShape(NavIconCornerRadius))
      .background(colors.glassSurface),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(NavIconSize),
      imageVector = icon,
      contentDescription = null,
      tint = colors.accent
    )
  }
}

@Composable
private fun TitleAndBody(
  modifier: Modifier = Modifier,
  title: String,
  body: String
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = title,
      style = ProfileTextStyles.rowTitle(),
      color = colors.onPrimaryContainer
    )
    Text(
      text = body,
      style = ProfileTextStyles.rowBody(),
      color = colors.onPrimaryContainer
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ProfileNavigationCard(
      icon = Icons.Default.Notifications,
      title = "Powiadomienia",
      body = "Poranny brief i alerty pogodowe",
      onClick = {}
    )
  }
}
