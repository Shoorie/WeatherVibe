package com.weather.vibe.core.designsystem.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

@Stable
class VibeBottomBarScrollBehavior internal constructor() {

  var isVisible: Boolean by mutableStateOf(true)
    internal set

  val nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
      when {
        available.y < -SCROLL_THRESHOLD_PX && isVisible -> isVisible = false
        available.y > SCROLL_THRESHOLD_PX && !isVisible -> isVisible = true
      }
      return Offset.Zero
    }
  }

  private companion object {
    const val SCROLL_THRESHOLD_PX = 4f
  }
}

@Composable
fun rememberVibeBottomBarScrollBehavior(): VibeBottomBarScrollBehavior =
  remember { VibeBottomBarScrollBehavior() }
