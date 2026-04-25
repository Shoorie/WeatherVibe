package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import java.time.format.DateTimeFormatter

internal object DayEntryDefaults {
  val ContentPadding = PaddingValues(
    horizontal = Padding.Medium,
    vertical = Padding.Small
  )
  val BorderWidth = 1.dp
  val ConditionEmojiSize = 22.sp
  const val BackgroundAlpha: Float = 0.32f
  const val BorderAlpha: Float = 0.55f
  val TimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
}
