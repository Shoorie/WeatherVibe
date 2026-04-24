package com.weather.vibe.feature.locations.ui.reorder

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.ui.LocationsKeys
import com.weather.vibe.feature.locations.ui.LocationsKeys.favoriteIdFromCardKey
import com.weather.vibe.feature.locations.ui.LocationsKeys.isCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

/**
 * Drives the drag-to-reorder interaction on the favorites list.
 *
 * Ownership of the on-screen order belongs to [orderedCards] — the LazyColumn renders from
 * it, and every drag mutates it immediately so the list visually reshuffles (Compose's
 * built-in item placement animations do the rest).
 *
 * While dragging, the lifted card is pinned under the finger via [translationYFor] using
 * the compensation formula `initialOffset + distance - currentOffset`. The current offset
 * is read live from the list, so scrolling (manual or auto) keeps the card aligned without
 * any extra book-keeping.
 *
 * After drop the final order is sent to [onCommit] and stored as a pending-commit buffer so
 * that [syncWithCards] keeps displaying the just-committed order until the DB round-trip
 * returns the matching list — eliminating the brief "snap back" flicker.
 *
 * For users who cannot perform the long-press drag gesture (notably TalkBack), [moveUp] and
 * [moveDown] shift a card by one slot and commit immediately, independent of pointer state.
 */
@Stable
internal class LocationsReorderState(
  private val listState: LazyListState,
  private val onCommit: (orderedIds: List<Long>) -> Unit
) {

  var orderedCards: ImmutableList<LocationCardUiState> by mutableStateOf(persistentListOf())
    private set

  var draggingFavoriteId: Long? by mutableStateOf(null)
    private set

  private var initialDraggedOffset: Int = 0
  private var draggedDistance: Float by mutableFloatStateOf(0f)
  private var pendingCommitIds: List<Long>? = null

  fun syncWithCards(cards: List<LocationCardUiState>) {

    if (draggingFavoriteId != null) return

    val pending = pendingCommitIds
    if (pending == null) {
      adoptCards(cards = cards)
      return
    }

    projectPendingOrderOnto(cards = cards, pending = pending)
  }

  fun onDragStart(favoriteId: Long) {

    val item = visibleCardItem(favoriteId = favoriteId) ?: return

    initialDraggedOffset = item.offset
    draggedDistance = 0f
    draggingFavoriteId = favoriteId
  }

  fun onDrag(deltaY: Float) {

    draggedDistance += deltaY
    trySwapWithHoveredCard()
  }

  fun onDragEnd() {

    if (draggingFavoriteId == null) return

    commitCurrentOrder()
    resetDragState()
  }

  fun isDragging(favoriteId: Long): Boolean =
    favoriteId == draggingFavoriteId

  fun translationYFor(favoriteId: Long): Float {

    if (favoriteId != draggingFavoriteId) return 0f
    val current = visibleCardItem(favoriteId = favoriteId) ?: return 0f

    return (initialDraggedOffset - current.offset).toFloat() + draggedDistance
  }

  fun autoScrollDirection(edgeZonePx: Float): Int {

    val center = visualCenterOfDragged() ?: return 0
    val info = listState.layoutInfo
    val topZone = info.viewportStartOffset + edgeZonePx
    val bottomZone = info.viewportEndOffset - edgeZonePx

    return when {
      center < topZone -> -1
      center > bottomZone -> 1
      else -> 0
    }
  }

  fun onAutoScrolled() =
    trySwapWithHoveredCard()

  fun canMoveUp(favoriteId: Long): Boolean =
    orderedCards.indexOfFirst { it.favoriteId == favoriteId } > 0

  fun canMoveDown(favoriteId: Long): Boolean {
    val index = orderedCards.indexOfFirst { it.favoriteId == favoriteId }
    return index in 0 until orderedCards.lastIndex
  }

  fun moveUp(favoriteId: Long) =
    shiftAndCommit(favoriteId = favoriteId, delta = -1)

  fun moveDown(favoriteId: Long) =
    shiftAndCommit(favoriteId = favoriteId, delta = 1)

  private fun adoptCards(cards: List<LocationCardUiState>) {
    if (orderedCards.matches(cards)) return
    orderedCards = cards.toImmutableList()
  }

  private fun projectPendingOrderOnto(cards: List<LocationCardUiState>, pending: List<Long>) {

    val cardsById = cards.associateBy { it.favoriteId }
    orderedCards = pending.mapNotNull { cardsById[it] }.toImmutableList()

    if (cards.matchesOrder(pending)) pendingCommitIds = null
  }

  private fun shiftAndCommit(favoriteId: Long, delta: Int) {

    val fromIndex = orderedCards.indexOfFirst { it.favoriteId == favoriteId }
    val toIndex = fromIndex + delta
    if (fromIndex == -1 || toIndex !in orderedCards.indices) return

    moveInOrder(fromIndex = fromIndex, toIndex = toIndex)
    commitCurrentOrder()
  }

  private fun trySwapWithHoveredCard() {

    val dragged = draggingFavoriteId ?: return
    val center = visualCenterOfDragged() ?: return

    val targetItem = findHoveredCard(center = center, excluding = dragged) ?: return
    val targetFavoriteId = favoriteIdFromCardKey(key = targetItem.key) ?: return

    swapDraggedBefore(targetFavoriteId = targetFavoriteId)
  }

  private fun swapDraggedBefore(targetFavoriteId: Long) {

    val dragged = draggingFavoriteId ?: return
    val fromIndex = orderedCards.indexOfFirst { it.favoriteId == dragged }
    val toIndex = orderedCards.indexOfFirst { it.favoriteId == targetFavoriteId }
    if (fromIndex == -1 || toIndex == -1 || fromIndex == toIndex) return

    moveInOrder(fromIndex = fromIndex, toIndex = toIndex)
  }

  private fun moveInOrder(fromIndex: Int, toIndex: Int) {
    orderedCards = orderedCards.toMutableList().apply {
      add(toIndex, removeAt(fromIndex))
    }.toImmutableList()
  }

  private fun commitCurrentOrder() {
    val finalIds = orderedCards.map { it.favoriteId }
    pendingCommitIds = finalIds
    onCommit(finalIds)
  }

  private fun resetDragState() {
    draggingFavoriteId = null
    draggedDistance = 0f
    initialDraggedOffset = 0
  }

  private fun visualCenterOfDragged(): Float? {

    val dragged = draggingFavoriteId ?: return null
    val item = visibleCardItem(favoriteId = dragged) ?: return null

    return initialDraggedOffset + item.size / 2f + draggedDistance
  }

  private fun findHoveredCard(center: Float, excluding: Long): LazyListItemInfo? {

    val excludedKey = LocationsKeys.card(favoriteId = excluding)

    return listState.layoutInfo.visibleItemsInfo.firstOrNull { info ->
      info.key != excludedKey &&
        isCard(key = info.key) &&
        center in info.offset.toFloat()..(info.offset + info.size).toFloat()
    }
  }

  private fun visibleCardItem(favoriteId: Long): LazyListItemInfo? =
    listState.layoutInfo.visibleItemsInfo
      .firstOrNull { it.key == LocationsKeys.card(favoriteId = favoriteId) }
}

private fun List<LocationCardUiState>.matches(other: List<LocationCardUiState>): Boolean {
  if (size != other.size) return false
  for (index in indices) if (this[index] !== other[index]) return false
  return true
}

private fun List<LocationCardUiState>.matchesOrder(orderedIds: List<Long>): Boolean {
  if (size != orderedIds.size) return false
  for (index in indices) if (this[index].favoriteId != orderedIds[index]) return false
  return true
}

@Composable
internal fun rememberLocationsReorderState(
  listState: LazyListState,
  cards: List<LocationCardUiState>,
  onCommit: (orderedIds: List<Long>) -> Unit
): LocationsReorderState {

  val state = remember(listState) {
    LocationsReorderState(listState = listState, onCommit = onCommit)
  }
  state.syncWithCards(cards = cards)

  return state
}
