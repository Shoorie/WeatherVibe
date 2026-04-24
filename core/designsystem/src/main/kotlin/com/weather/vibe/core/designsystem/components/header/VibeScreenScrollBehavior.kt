package com.weather.vibe.core.designsystem.components.header

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
class VibeScreenScrollBehavior internal constructor() {

  var isHeaderVisible: Boolean by mutableStateOf(true)
    internal set

  val nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {

    override fun onPostScroll(
      consumed: Offset,
      available: Offset,
      source: NestedScrollSource
    ): Offset {
      reactToScroll(consumed = consumed, available = available)
      return Offset.Zero
    }
  }

  private fun reactToScroll(consumed: Offset, available: Offset) {
    when {
      userPullsContentDown(consumed = consumed, available = available) -> show()
      contentActuallyScrollsUp(consumed) -> hide()
    }
  }

  private fun userPullsContentDown(consumed: Offset, available: Offset): Boolean =
    consumed.y > SCROLL_THRESHOLD_PX || available.y > SCROLL_THRESHOLD_PX

  private fun contentActuallyScrollsUp(consumed: Offset): Boolean =
    consumed.y < -SCROLL_THRESHOLD_PX

  fun show() {
    isHeaderVisible = true
  }

  private fun hide() {
    isHeaderVisible = false
  }

  private companion object {
    const val SCROLL_THRESHOLD_PX = 4f
  }
}

@Composable
fun rememberVibeScreenScrollBehavior(): VibeScreenScrollBehavior =
  remember { VibeScreenScrollBehavior() }
