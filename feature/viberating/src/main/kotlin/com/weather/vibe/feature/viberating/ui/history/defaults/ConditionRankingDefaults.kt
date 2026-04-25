package com.weather.vibe.feature.viberating.ui.history.defaults

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object ConditionRankingDefaults {
  val ProgressHeight = 6.dp
  val ProgressShape = RoundedCornerShape(6.dp)
  val EmojiSize = 24.sp
  const val AverageRatingFormat: String = "%.1f"
  const val ProgressMinFraction: Float = 0f
  const val ProgressMaxFraction: Float = 1f
}
