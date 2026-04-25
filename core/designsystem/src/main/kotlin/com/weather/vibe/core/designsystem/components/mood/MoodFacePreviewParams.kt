package com.weather.vibe.core.designsystem.components.mood

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults.Size

@Immutable
internal data class MoodFacePreviewParams(
  val rating: Int,
  val active: Boolean = false,
  val size: Dp = Size,
  val contentDescription: String? = null
)
