package com.weather.vibe.core.designsystem.components.button

import android.R
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

private val BrandContent = Color.White
private val BrandMinHeight = 52.dp

@Composable
fun BrandButton(
  modifier: Modifier = Modifier,
  icon: Painter,
  text: String,
  containerColor: Color,
  onClick: () -> Unit
) {
  Button(
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = BrandMinHeight),
    onClick = onClick,
    shape = shapes.pill,
    contentPadding = PaddingValues(horizontal = Padding.Large, vertical = Padding.Small),
    colors = ButtonDefaults.buttonColors(
      containerColor = containerColor,
      contentColor = BrandContent
    )
  ) {
    Icon(
      painter = icon,
      contentDescription = null,
      modifier = Modifier.size(IconSize.Small),
      tint = BrandContent
    )
    Spacer(modifier = Modifier.width(Padding.Small))
    Text(
      text = text,
      color = BrandContent,
      style = typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
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
