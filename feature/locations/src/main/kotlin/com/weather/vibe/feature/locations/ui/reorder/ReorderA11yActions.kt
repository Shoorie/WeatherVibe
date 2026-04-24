package com.weather.vibe.feature.locations.ui.reorder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.actionMoveDown
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.actionMoveUp

/**
 * Exposes drag-to-reorder as TalkBack-reachable "Move up" / "Move down"
 * actions. TalkBack users cannot perform a long-press drag, so without
 * these the reorder feature would be unreachable for them.
 *
 * Returns an unchanged modifier when reordering is disabled (compare
 * mode or locked row) or when the row has no valid neighbour to move into
 * (first row → no "up", last → no "down").
 */
@Composable
internal fun Modifier.reorderA11yActions(
  favoriteId: Long,
  reorder: LocationsReorderState,
  enabled: Boolean
): Modifier {

  if (!enabled) return this

  val actions = buildReorderActions(favoriteId = favoriteId, reorder = reorder)
  if (actions.isEmpty()) return this

  return this.semantics { customActions = actions }
}

@Composable
private fun buildReorderActions(
  favoriteId: Long,
  reorder: LocationsReorderState
): List<CustomAccessibilityAction> =
  buildList {
    if (reorder.canMoveUp(favoriteId = favoriteId)) {
      add(moveUpAction(favoriteId = favoriteId, reorder = reorder, label = actionMoveUp()))
    }
    if (reorder.canMoveDown(favoriteId = favoriteId)) {
      add(moveDownAction(favoriteId = favoriteId, reorder = reorder, label = actionMoveDown()))
    }
  }

private fun moveUpAction(
  favoriteId: Long,
  reorder: LocationsReorderState,
  label: String
): CustomAccessibilityAction =
  CustomAccessibilityAction(label = label) {
    reorder.moveUp(favoriteId = favoriteId)
    true
  }

private fun moveDownAction(
  favoriteId: Long,
  reorder: LocationsReorderState,
  label: String
): CustomAccessibilityAction =
  CustomAccessibilityAction(label = label) {
    reorder.moveDown(favoriteId = favoriteId)
    true
  }
