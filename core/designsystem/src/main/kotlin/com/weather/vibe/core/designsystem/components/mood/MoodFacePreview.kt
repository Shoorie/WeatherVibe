package com.weather.vibe.core.designsystem.components.mood

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults.SizeLarge
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults.SizeSmall

internal class MoodFacePreview : PreviewParameterProvider<MoodFacePreviewParams> {

  private val inactiveLowest: MoodFacePreviewParams =
    MoodFacePreviewParams(rating = 1)

  private val activeMiddle: MoodFacePreviewParams =
    MoodFacePreviewParams(rating = 3, active = true)

  private val activeHighestLarge: MoodFacePreviewParams =
    MoodFacePreviewParams(rating = 5, active = true, size = SizeLarge)

  private val activeSmall: MoodFacePreviewParams =
    MoodFacePreviewParams(rating = 4, active = true, size = SizeSmall)

  private val outOfRangeCoerced: MoodFacePreviewParams =
    MoodFacePreviewParams(rating = 9, active = true)

  override val values: Sequence<MoodFacePreviewParams> =
    sequenceOf(
      inactiveLowest,
      activeMiddle,
      activeHighestLarge,
      activeSmall,
      outOfRangeCoerced
    )
}
