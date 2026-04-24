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
 * While a card is being dragged, nudges the list whenever the card enters the edge
 * zone at the top or bottom of the viewport — so slots outside the current viewport
 * stay reachable without lifting the finger.
 */
@Composable
internal fun LocationsReorderAutoScroller(
  listState: LazyListState,
  reorderState: LocationsReorderState,
  edgeZone: Dp,
  pixelsPerSecond: Dp
) {
  val density = LocalDensity.current
  val edgeZonePx = remember(density, edgeZone) { with(density) { edgeZone.toPx() } }
  val pixelsPerSecondValue = remember(density, pixelsPerSecond) {
    with(density) { pixelsPerSecond.toPx() }
  }
  val draggingFavoriteId = reorderState.draggingFavoriteId

  LaunchedEffect(draggingFavoriteId) {
    if (draggingFavoriteId == null) return@LaunchedEffect
    var lastFrameNanos = withFrameNanos { it }
    while (isActive) {
      val now = withFrameNanos { it }
      val elapsedSeconds = (now - lastFrameNanos) / NANOS_PER_SECOND
      lastFrameNanos = now
      val direction = reorderState.autoScrollDirection(edgeZonePx = edgeZonePx)
      if (direction == 0) continue
      val delta = direction * pixelsPerSecondValue * elapsedSeconds
      listState.scroll { scrollBy(delta) }
      reorderState.onAutoScrolled()
    }
  }
}

private const val NANOS_PER_SECOND: Float = 1_000_000_000f
