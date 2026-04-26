package com.weather.vibe.feature.viberating.preview

import androidx.compose.runtime.Immutable

@Immutable
internal data class PatternsLockedCardPreviewParams(
  val entriesSoFar: Int,
  val unlockThreshold: Int
)
