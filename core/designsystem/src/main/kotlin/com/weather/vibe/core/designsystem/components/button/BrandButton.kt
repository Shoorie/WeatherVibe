package com.weather.vibe.core.designsystem.components.button

import android.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.ButtonDefaults.BrandContent
import com.weather.vibe.core.designsystem.components.button.ButtonDefaults.BrandContentPadding
import com.weather.vibe.core.designsystem.components.button.ButtonDefaults.BrandMinHeight
import com.weather.vibe.core.designsystem.components.button.ButtonDefaults.brandButtonColors
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

@Composable
fun BrandButton(
  modifier: Modifier = Modifier,
  icon: Painter,
  text: String,
  containerColor: Color,
  enabled: Boolean = true,
  disabledStateDescription: String? = null,
  onClick: () -> Unit
) {

  val titleSmall = typography.titleSmall
  val labelStyle = remember(titleSmall) { titleSmall.copy(fontWeight = SemiBold) }

  Button(
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = BrandMinHeight)
      .semantics {
        if (!enabled && disabledStateDescription != null) {
          stateDescription = disabledStateDescription
        }
      },
    onClick = onClick,
    enabled = enabled,
    shape = shapes.pill,
    contentPadding = BrandContentPadding,
    colors = brandButtonColors(containerColor)
  ) {
    Icon(
      modifier = Modifier.size(IconSize.Small),
      painter = icon,
      contentDescription = null,
      tint = BrandContent
    )
    Spacer(modifier = Modifier.width(Small))
    Text(
      text = text,
      color = BrandContent,
      style = labelStyle
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BrandButton(
      icon = painterResource(id = R.drawable.ic_media_play),
      text = "Open in App",
      containerColor = BrandPreviewColor,
      onClick = {}
    )
  }
}

private val BrandPreviewColor = Color(0xFF1DB954)
