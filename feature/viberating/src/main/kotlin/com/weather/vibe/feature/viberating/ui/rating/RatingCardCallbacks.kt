package com.weather.vibe.feature.viberating.ui.rating

internal data class RatingCardCallbacks(
  val onSliderValueChanged: (Int) -> Unit,
  val onNoteValueChanged: (String) -> Unit,
  val onNoteExpandClick: () -> Unit,
  val onNoteCollapseClick: () -> Unit,
  val onSaveClicked: () -> Unit,
  val onRetryClicked: () -> Unit,
  val onDismissErrorClicked: () -> Unit,
  val onViewHistoryClicked: () -> Unit
)
