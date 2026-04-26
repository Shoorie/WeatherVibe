package com.weather.vibe.core.designsystem.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.header.VibeScreenDefaults.BackButtonTouch
import com.weather.vibe.core.designsystem.components.header.VibeScreenDefaults.BackButtonVisual
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun VibeScreenHeader(
  title: String,
  modifier: Modifier = Modifier,
  subtitle: String? = null,
  onBackClicked: (() -> Unit)? = null,
  backContentDescription: String? = null,
  trailing: (@Composable RowScope.() -> Unit)? = null
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = Medium)
      .padding(top = ExtraSmall, bottom = Medium)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(Padding.Small)
    ) {
      if (onBackClicked != null) {
        VibeHeaderBackButton(
          onClick = onBackClicked,
          contentDescription = backContentDescription
        )
        Spacer(Modifier.size(ExtraSmall))
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          modifier = Modifier.semantics { heading() },
          text = title,
          style = typography.headlineMedium,
          color = colors.onSurface,
          fontWeight = FontWeight.Bold
        )
        if (subtitle != null) {
          Text(
            text = subtitle,
            style = typography.bodySmall,
            color = colors.onSurfaceVariant
          )
        }
      }
      if (trailing != null) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(ExtraSmall),
          content = trailing
        )
      }
    }
  }
}

@Composable
private fun VibeHeaderBackButton(
  onClick: () -> Unit,
  contentDescription: String?
) {
  IconButton(
    onClick = onClick,
    modifier = Modifier.size(BackButtonTouch)
  ) {
    Box(
      modifier = Modifier
        .size(BackButtonVisual)
        .clip(CircleShape)
        .background(colors.glassSurface),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = contentDescription,
        tint = colors.onSurface
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun TitleOnlyPreview() {
  WeatherVibeTheme {
    VibeScreenHeader(title = "Profile")
  }
}

@PreviewLightDark
@Composable
private fun TitleWithSubtitlePreview() {
  WeatherVibeTheme {
    VibeScreenHeader(
      title = "Your vibe",
      subtitle = "How you rated the last few days"
    )
  }
}

@PreviewLightDark
@Composable
private fun WithBackAndSubtitlePreview() {
  WeatherVibeTheme {
    VibeScreenHeader(
      title = "Personalization",
      subtitle = "Brief tone, units, and favorite genres",
      onBackClicked = {},
      backContentDescription = "Back"
    )
  }
}
