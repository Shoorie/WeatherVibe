package com.weather.vibe.feature.viberating.ui.rating

internal data class RatingCardCallbacks(
  val onSliderValueChange: (Int) -> Unit,
  val onNoteValueChange: (String) -> Unit,
  val onNoteExpandClick: () -> Unit,
  val onNoteCollapseClick: () -> Unit,
  val onSaveClick: () -> Unit,
  val onRetryClick: () -> Unit,
  val onDismissErrorClick: () -> Unit,
  val onViewHistoryClick: () -> Unit
) {
  companion object {
    val Noop: RatingCardCallbacks = RatingCardCallbacks(
      onSliderValueChange = {},
      onNoteValueChange = {},
      onNoteExpandClick = {},
      onNoteCollapseClick = {},
      onSaveClick = {},
      onRetryClick = {},
      onDismissErrorClick = {},
      onViewHistoryClick = {}
    )
  }
}
