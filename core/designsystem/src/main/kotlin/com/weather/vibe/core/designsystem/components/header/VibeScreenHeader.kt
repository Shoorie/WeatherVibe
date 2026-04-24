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
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
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
      .padding(horizontal = Padding.Medium, vertical = Padding.Medium)
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
        Spacer(Modifier.size(Padding.ExtraSmall))
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = typography.headlineMedium,
          color = colors.onSurface,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.semantics { heading() }
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
          horizontalArrangement = Arrangement.spacedBy(Padding.ExtraSmall),
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
    modifier = Modifier.size(BACK_BUTTON_TOUCH)
  ) {
    Box(
      modifier = Modifier
        .size(BACK_BUTTON_VISUAL)
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

private val BACK_BUTTON_TOUCH = 48.dp
private val BACK_BUTTON_VISUAL = 40.dp

@PreviewLightDark
@Composable
private fun TitleOnlyPreview() {
  WeatherVibeTheme {
    VibeScreenHeader(title = "Profil")
  }
}

@PreviewLightDark
@Composable
private fun TitleWithSubtitlePreview() {
  WeatherVibeTheme {
    VibeScreenHeader(
      title = "Twój vibe",
      subtitle = "Jak oceniałeś ostatnie dni"
    )
  }
}

@PreviewLightDark
@Composable
private fun WithBackAndSubtitlePreview() {
  WeatherVibeTheme {
    VibeScreenHeader(
      title = "Personalizacja",
      subtitle = "Ton briefu, jednostki i ulubione gatunki",
      onBackClicked = {},
      backContentDescription = "Wstecz"
    )
  }
}
