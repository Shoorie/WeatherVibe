package com.weather.vibe.feature.home.ui.component.share

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.weather.vibe.feature.home.presentation.state.SharePosterUiState
import com.weather.vibe.feature.home.ui.component.share.SharePosterDefaults.CaptureDensity
import kotlinx.coroutines.flow.first

@Composable
internal fun PosterCaptureHost(
  state: SharePosterUiState,
  onCaptured: (Bitmap) -> Unit
) {

  val graphicsLayer = rememberGraphicsLayer()
  val captureDensity = remember { Density(density = CaptureDensity) }
  var hasDrawn by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .alpha(0f),
    contentAlignment = Alignment.Center
  ) {
    CompositionLocalProvider(LocalDensity provides captureDensity) {
      Box(
        modifier = Modifier.drawWithContent {
          graphicsLayer.record { this@drawWithContent.drawContent() }
          drawLayer(graphicsLayer)
          if (!hasDrawn) hasDrawn = true
        },
        content = { SharePoster(state = state) }
      )
    }
  }

  LaunchedEffect(state) {
    snapshotFlow { hasDrawn }.first { it }
    val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
    onCaptured(bitmap)
  }
}
