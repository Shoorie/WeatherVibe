package com.weather.vibe.feature.locations.ui.reorder

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.isActive

/**
 * Drives edge-zone auto-scroll while a card is being dragged.
 *
 * LazyColumn does not auto-scroll on its own when a pointer sits near the viewport edge —
 * the pointer gesture and the list's scroll state live in separate systems. Without this
 * driver the user cannot drag a card past the visible viewport, which makes slots off the
 * first/last screen unreachable without lifting the finger.
 *
 * The composable is a no-op while no card is being dragged (the `LaunchedEffect` key flips
 * to `null` and cancels the frame loop), so it only consumes the choreographer clock during
 * an active drag.
 */
@Composable
internal fun LocationsReorderAutoScroller(
  listState: LazyListState,
  reorder: LocationsReorderState,
  edgeZone: Dp,
  pixelsPerSecond: Dp
) {
  val density = LocalDensity.current
  val edgeZonePx = remember(density, edgeZone) { with(density) { edgeZone.toPx() } }
  val speedPxPerSecond = remember(density, pixelsPerSecond) {
    with(density) { pixelsPerSecond.toPx() }
  }

  LaunchedEffect(reorder.draggingFavoriteId) {

    if (reorder.draggingFavoriteId == null) return@LaunchedEffect

    var lastFrameNanos = withFrameNanos { it }
    while (isActive) {

      val now = withFrameNanos { it }
      val elapsedSeconds = (now - lastFrameNanos) / NANOS_PER_SECOND
      lastFrameNanos = now

      val direction = reorder.autoScrollDirection(edgeZonePx = edgeZonePx)
      if (direction == 0) continue

      val delta = direction * speedPxPerSecond * elapsedSeconds
      listState.scroll { scrollBy(delta) }
      reorder.onAutoScrolled()
    }
  }
}

private const val NANOS_PER_SECOND: Float = 1_000_000_000f
