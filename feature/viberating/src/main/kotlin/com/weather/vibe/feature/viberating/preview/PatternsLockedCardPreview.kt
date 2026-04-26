package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class PatternsLockedCardPreview :
  PreviewParameterProvider<PatternsLockedCardPreviewParams> {

  private val earlyProgress: PatternsLockedCardPreviewParams =
    PatternsLockedCardPreviewParams(
      entriesSoFar = 2,
      unlockThreshold = 14
    )

  private val midProgress: PatternsLockedCardPreviewParams =
    PatternsLockedCardPreviewParams(
      entriesSoFar = 6,
      unlockThreshold = 14
    )

  private val almostUnlocked: PatternsLockedCardPreviewParams =
    PatternsLockedCardPreviewParams(
      entriesSoFar = 12,
      unlockThreshold = 14
    )

  override val values: Sequence<PatternsLockedCardPreviewParams> =
    sequenceOf(earlyProgress, midProgress, almostUnlocked)
}
