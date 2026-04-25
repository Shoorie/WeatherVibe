package com.weather.vibe.feature.viberating.preview

import androidx.compose.runtime.Immutable

@Immutable
internal data class NoteEditorPreviewParams(
  val expanded: Boolean,
  val enabled: Boolean,
  val note: String
)
