package com.weather.vibe.core.designsystem.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Elevation.Card
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

private val DefaultContentPadding = PaddingValues(Padding.Medium)

@Composable
fun GlassCard(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = DefaultContentPadding,
  onClick: (() -> Unit)? = null,
  onClickLabel: String? = null,
  content: @Composable ColumnScope.() -> Unit
) {

  val surfaceColor = colors.glassSurface

  Column(
    modifier = modifier
      .shadow(elevation = Card, shape = shapes.card, clip = false)
      .clip(shapes.card)
      .drawBehind { drawRect(surfaceColor) }
      .then(
        if (onClick != null) {
          Modifier
            .minimumInteractiveComponentSize()
            .clickable(
              onClickLabel = onClickLabel,
              role = Role.Button,
              onClick = onClick
            )
        } else Modifier
      )
      .padding(contentPadding),
    content = content
  )
}

@PreviewLightDark
@Composable
private fun GlassCardPreview() {
  WeatherVibeTheme {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "Title",
        style = typography.titleSmall,
        color = colors.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(Small))
      Text(
        text = "Content inside a GlassCard",
        style = typography.bodyMedium,
        color = colors.onBackground
      )
    }
  }
}
