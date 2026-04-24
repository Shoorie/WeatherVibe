package com.weather.vibe.feature.locations.ui.reorder

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * Attaches a long-press drag gesture that hands the lifted card off to [reorderState].
 * When [enabled] is false the modifier is a no-op — callers pass false in compare mode
 * or for locked cards so those gestures stay free for other affordances.
 */
@Composable
internal fun Modifier.dragToReorder(
  favoriteId: Long,
  reorderState: LocationsReorderState,
  enabled: Boolean
): Modifier {
  if (!enabled) return this
  val haptic = LocalHapticFeedback.current
  return this.pointerInput(favoriteId) {
    detectDragGesturesAfterLongPress(
      onDragStart = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        reorderState.onDragStart(favoriteId = favoriteId)
      },
      onDragEnd = { reorderState.onDragEnd() },
      onDragCancel = { reorderState.onDragEnd() },
      onDrag = { _, drag -> reorderState.onDrag(deltaY = drag.y) }
    )
  }
}
